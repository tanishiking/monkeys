package monkeys
package parser

import scanner.{Scanner, Token}
import ast.*
import monkeys.scanner.TokenType
import scala.collection.mutable.ListBuffer

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
  private def expectPeek(expect: TokenType): Option[Unit] =
    if (peekToken.is(expect)) then
      nextToken()
      Some(())
    else None

  // Read two tokens, so curToken and peekToken are both set”
  nextToken()
  nextToken()

  def parseProgram(): Program =
    val stmts = ListBuffer[Statement]()
    while !currToken.isEOF do
      parseStatement().foreach { stmt =>
        stmts.append(stmt)
      }
      nextToken()
    Program(stmts.toList)

  def parseStatement(): Option[Statement] = currToken.tokenType match
    case TokenType.LET =>
      parseLetStatement()
    case _ => None

  def parseLetStatement(): Option[LetStatement] =
    val tok = currToken
    for
      _ <- expectPeek(TokenType.IDENT)
      name = Identifier(currToken.literal, currToken)
      _ <- expectPeek(TokenType.ASSIGN)
    yield
      // TODO
      while !currToken.is(TokenType.SEMICOLON) do nextToken()
      LetStatement(name, IntExpr(0, Token(TokenType.INT, "0")), name.token)
  end parseLetStatement

end Parser
