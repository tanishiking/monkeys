package monkeys.scanner

enum TokenType:
  case ILLEGAL
  case EOF

  // Identifiers + literals
  case IDENT // add, foobar, x, y, ...
  case INT // 1343456

  // Operators
  case ASSIGN
  case PLUS
  case MINUS
  case BANG // !
  case ASTERISK
  case SLASH
  case EQ // ==
  case NEQ // !=

  case LT
  case GT

  // Delimiters
  case COMMA
  case SEMICOLON

  case LPAREN
  case RPAREN
  case LBRACE
  case RBRACE

  // Keywords
  case FUNCTION
  case LET
  case TRUE
  case FALSE
  case IF
  case ELSE
  case RETURN

case class Token(
    tokenType: TokenType,
    literal: String,
):
  def isEOF = tokenType == TokenType.EOF

val keywords: Map[String, TokenType] = Map(
  "fn" -> TokenType.FUNCTION,
  "let" -> TokenType.LET,
  "true" -> TokenType.TRUE,
  "false" -> TokenType.FALSE,
  "if" -> TokenType.IF,
  "else" -> TokenType.ELSE,
  "return" -> TokenType.RETURN,
)
