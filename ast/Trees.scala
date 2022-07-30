package monkeys
package ast

import monkeys.scanner.Token

sealed abstract class Node:
  val token: Token
  def tokenLiteral: String = token.literal // for debugging

trait Statement extends Node
trait Expr extends Node

case class Program(stmts: List[Statement]) extends Node:
  override val token: Token = stmts.head.token

case class LetStatement(name: Identifier, value: Expr, token: Token)
    extends Statement

case class Identifier(value: String, token: Token) extends Expr

case class IntExpr(value: Int, token: Token) extends Expr
