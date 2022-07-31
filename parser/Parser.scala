package monkeys
package parser

import scanner.{Scanner, Token}
import ast.*
import monkeys.scanner.TokenType
import scala.collection.mutable.ListBuffer

case class Parsed(program: Program, errors: List[ParseError])

class Parser(scanner: Scanner):
  private var currToken: Token = _
  private var peekToken: Token = _

  private def nextToken(): Unit =
    currToken = peekToken
    peekToken = scanner.nextToken()

  /** `peekToken` and check it's given expected token type.
    * If it's expected one, read token and return true,
    * otherwise, return false.
    *
    * @param tokenType expected token type
    */
  private def expectPeek(expect: TokenType): Either[UnexpectedToken, Unit] =
    if (peekToken.is(expect)) then
      nextToken()
      Right(())
    else
      Left(
        UnexpectedToken(expect, peekToken.tokenType),
      )

  // Read two tokens, so curToken and peekToken are both set”
  nextToken()
  nextToken()

  def parse(): Parsed =
    val (prog, errors) = parseProgram()
    Parsed(prog, errors)

  def parseProgram(): (Program, List[ParseError]) =
    val stmts = ListBuffer[Statement]()
    val errors = ListBuffer[ParseError]()
    while !currToken.isEOF do
      parseStatement() match
        case Left(error) => errors.append(error)
        case Right(stmt) => stmts.append(stmt)
      nextToken()
    (Program(stmts.toList), errors.toList)

  def parseStatement(): Either[ParseError, Statement] =
    currToken.tokenType match
      case TokenType.LET =>
        parseLetStatement()
      case _ =>
        Left(UnexpectedToken("begining of statement", currToken.tokenType))

  def parseLetStatement(): Either[ParseError, LetStatement] =
    val tok = currToken
    (for
      _ <- expectPeek(TokenType.IDENT)
      name = Identifier(currToken.literal, currToken)
      _ <- expectPeek(TokenType.ASSIGN)
    yield name) match {
      case Left(e) =>
        // error recover, skip to next ";"
        while !currToken.is(TokenType.SEMICOLON) do nextToken()
        Left(e)
      case Right(name) =>
        // TODO
        while !currToken.is(TokenType.SEMICOLON) do nextToken()
        Right(
          LetStatement(name, IntExpr(0, Token(TokenType.INT, "0")), name.token),
        )
    }

  end parseLetStatement

end Parser
