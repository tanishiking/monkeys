package monkeys
package parser

import monkeys.scanner.TokenType

class ParseError(msg: String) extends Error(msg)

case class UnexpectedToken private (msg: String) extends ParseError(msg)
object UnexpectedToken:
  def apply(expect: TokenType, actual: TokenType): UnexpectedToken =
    UnexpectedToken(s"Expected next token $expect, but got $actual.")

  def apply(expect: String, actual: TokenType): UnexpectedToken =
    UnexpectedToken(s"Expected $expect, but got $actual.")
