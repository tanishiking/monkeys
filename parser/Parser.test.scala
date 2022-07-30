package monkeys
package parser

import monkeys.scanner.Scanner
import monkeys.ast.*
import munit.Location

class ParserTests extends munit.FunSuite:
  test("let-statement") {
    val input = """
    |let x = 5;
    |let y = 10;
    |let foobar = 838383;
    |""".stripMargin

    val scanner = new Scanner(input)
    val parser = new Parser(scanner)

    val expects = Seq[(String, Int)](
      ("x", 5),
      ("y", 10),
      ("foobar", 838383),
    )
    val program = parser.parseProgram()
    assertEquals(program.stmts.length, expects.length)

    program.stmts.zip(expects).map { case (stmt, (name, _)) =>
      checkLetStatement(stmt, name)
    }
  }

  private def checkLetStatement(stmt: Statement, name: String)(implicit
      loc: Location,
  ) =
    stmt match
      case l: LetStatement =>
        assertEquals(l.name.value, name)
        assertEquals(l.tokenLiteral, name)
      case _ =>
        fail(s"LetStatement is expected, but actual: $stmt")
