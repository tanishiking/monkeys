package monkeys.scanner

class Scanner(input: String):
  private var currChar: Char = 0
  private var pos: Int = 0 // current position in input (points to current char)
  private var readPos: Int = 0

  readChar

  def nextToken(): Token =
    def readOne(tok: Token) =
      readChar
      tok
    skipWhitespace
    val s = currChar.toString()
    val tok = currChar match // 0 represents ASCII code for NUL
      case '=' if peekChar == '=' =>
        readChar
        readOne(Token(TokenType.EQ, "=="))
      case '=' => readOne(Token(TokenType.ASSIGN, s))
      case ';' => readOne(Token(TokenType.SEMICOLON, s))
      case '(' => readOne(Token(TokenType.LPAREN, s))
      case ')' => readOne(Token(TokenType.RPAREN, s))
      case ',' => readOne(Token(TokenType.COMMA, s))
      case '+' => readOne(Token(TokenType.PLUS, s))
      case '-' => readOne(Token(TokenType.MINUS, s))
      case '!' if peekChar == '=' =>
        readChar
        readOne(Token(TokenType.NEQ, "!="))
      case '!' => readOne(Token(TokenType.BANG, s))
      case '*' => readOne(Token(TokenType.ASTERISK, s))
      case '/' => readOne(Token(TokenType.SLASH, s))

      case '<' => readOne(Token(TokenType.LT, s))
      case '>' => readOne(Token(TokenType.GT, s))

      case '{' => readOne(Token(TokenType.LBRACE, s))
      case '}' => readOne(Token(TokenType.RBRACE, s))
      case 0   => readOne(Token(TokenType.EOF, ""))
      case _ =>
        if currChar.isLetter then
          val id = readIdentifier
          val typ = keywords.getOrElse(id, TokenType.IDENT)
          Token(typ, id)
        else if currChar.isDigit then
          val num = readNumber
          Token(TokenType.INT, num)
        else Token(TokenType.ILLEGAL, s)
    tok

  private def readChar: Unit =
    if readPos >= input.length then currChar = 0
    else
      currChar = input.charAt(readPos)
      pos = readPos
      readPos += 1

  private def peekChar: Char =
    if readPos >= input.length then 0
    else input.charAt(readPos)

  private def readNumber: String =
    val start = pos
    while currChar.isDigit do readChar
    input.substring(start, pos)

  private def readIdentifier: String =
    val start = pos
    while currChar.isLetter do readChar
    input.substring(start, pos)

  private def skipWhitespace: Unit =
    while currChar.isWhitespace
    do readChar

  extension (ch: Char)
    private def isWhitespace: Boolean =
      ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r'

    private def isLetter: Boolean =
      'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_'

    private def isDigit: Boolean =
      '0' <= ch && ch <= '9'
  end extension
