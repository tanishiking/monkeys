package monkeys.scanner

class SnannerTests extends munit.FunSuite {
  test("basic") {
    val input = "=+(){},;"
    val sc = new Scanner(input)

    val expected: List[Token] = List(
      Token(TokenType.ASSIGN, "="),
      Token(TokenType.PLUS, "+"),
      Token(TokenType.LPAREN, "("),
      Token(TokenType.RPAREN, ")"),
      Token(TokenType.LBRACE, "{"),
      Token(TokenType.RBRACE, "}"),
      Token(TokenType.COMMA, ","),
      Token(TokenType.SEMICOLON, ";"),
    )

    val buf = scala.collection.mutable.ListBuffer[Token]()
    var tok = sc.nextToken
    while !tok.isEOF do
      buf.append(tok)
      tok = sc.nextToken
    val actual = buf.toList
    assertEquals(actual, expected)
  }

  test("advanced") {
    val input = """
    |let five = 5;
    |let ten = 10;
    |
    |let add = fn(x, y) {
    |  x + y;
    |};
    |
    |let result = add(five, ten);
    |!-/*5;
    |5 < 10 > 5;
    |
    |if (5 < 10) {
    |    return true;
    |} else {
    |    return false;
    |}
    |
    |10 == 10
    |10 != 9
    |""".stripMargin

    val sc = new Scanner(input)

    val expected: List[Token] = List(
      Token(TokenType.LET, "let"),
      Token(TokenType.IDENT, "five"),
      Token(TokenType.ASSIGN, "="),
      Token(TokenType.INT, "5"),
      Token(TokenType.SEMICOLON, ";"),
      // let ten = 10
      Token(TokenType.LET, "let"),
      Token(TokenType.IDENT, "ten"),
      Token(TokenType.ASSIGN, "="),
      Token(TokenType.INT, "10"),
      Token(TokenType.SEMICOLON, ";"),
      // let add = fn ...
      Token(TokenType.LET, "let"),
      Token(TokenType.IDENT, "add"),
      Token(TokenType.ASSIGN, "="),
      Token(TokenType.FUNCTION, "fn"),
      Token(TokenType.LPAREN, "("),
      Token(TokenType.IDENT, "x"),
      Token(TokenType.COMMA, ","),
      Token(TokenType.IDENT, "y"),
      Token(TokenType.RPAREN, ")"),
      Token(TokenType.LBRACE, "{"),
      Token(TokenType.IDENT, "x"),
      Token(TokenType.PLUS, "+"),
      Token(TokenType.IDENT, "y"),
      Token(TokenType.SEMICOLON, ";"),
      Token(TokenType.RBRACE, "}"),
      Token(TokenType.SEMICOLON, ";"),
      // let result =
      Token(TokenType.LET, "let"),
      Token(TokenType.IDENT, "result"),
      Token(TokenType.ASSIGN, "="),
      Token(TokenType.IDENT, "add"),
      Token(TokenType.LPAREN, "("),
      Token(TokenType.IDENT, "five"),
      Token(TokenType.COMMA, ","),
      Token(TokenType.IDENT, "ten"),
      Token(TokenType.RPAREN, ")"),
      Token(TokenType.SEMICOLON, ";"),
      // !-/*5
      Token(TokenType.BANG, "!"),
      Token(TokenType.MINUS, "-"),
      Token(TokenType.SLASH, "/"),
      Token(TokenType.ASTERISK, "*"),
      Token(TokenType.INT, "5"),
      Token(TokenType.SEMICOLON, ";"),
      // 5 < 10 > 5
      Token(TokenType.INT, "5"),
      Token(TokenType.LT, "<"),
      Token(TokenType.INT, "10"),
      Token(TokenType.GT, ">"),
      Token(TokenType.INT, "5"),
      Token(TokenType.SEMICOLON, ";"),

      // if (5 < 10)
      Token(TokenType.IF, "if"),
      Token(TokenType.LPAREN, "("),
      Token(TokenType.INT, "5"),
      Token(TokenType.LT, "<"),
      Token(TokenType.INT, "10"),
      Token(TokenType.RPAREN, ")"),
      Token(TokenType.LBRACE, "{"),
      Token(TokenType.RETURN, "return"),
      Token(TokenType.TRUE, "true"),
      Token(TokenType.SEMICOLON, ";"),
      Token(TokenType.RBRACE, "}"),
      Token(TokenType.ELSE, "else"),
      Token(TokenType.LBRACE, "{"),
      Token(TokenType.RETURN, "return"),
      Token(TokenType.FALSE, "false"),
      Token(TokenType.SEMICOLON, ";"),
      Token(TokenType.RBRACE, "}"),
      // 10 == 10
      Token(TokenType.INT, "10"),
      Token(TokenType.EQ, "=="),
      Token(TokenType.INT, "10"),
      // 10 != 9
      Token(TokenType.INT, "10"),
      Token(TokenType.NEQ, "!="),
      Token(TokenType.INT, "9"),
    )

    val buf = scala.collection.mutable.ListBuffer[Token]()
    var tok = sc.nextToken
    while !tok.isEOF do
      buf.append(tok)
      tok = sc.nextToken
    val actual = buf.toList
    // actual.zip(expected).foreach((a, e) => println(s"$a vs $e"))
    assertEquals(actual, expected)
  }
}
