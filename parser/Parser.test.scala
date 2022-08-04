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
    val parsed = parser.parse()
    checkNoParseErrors(parsed)
    assertEquals(parsed.program.stmts.length, expects.length)

    parsed.program.stmts.zip(expects).map { case (stmt, (name, _)) =>
      checkLetStatement(stmt, name)
    }
  }

  test("return-statement") {
    val input = """
    |return 5;
    |return 10;
    |return 993322;
    |""".stripMargin
    val scanner = new Scanner(input)
    val parser = new Parser(scanner)

    val expects = Seq[(String, Int)](
      ("x", 5),
      ("y", 10),
      ("foobar", 838383),
    )
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

  private def checkNoParseErrors(parsed: Parsed)(implicit loc: Location): Unit =
    if parsed.errors.isEmpty then ()
    else
      val errs = parsed.errors.map(e => e.getMessage()).mkString("\n")
      fail(s"Found ${parsed.errors.length} parse errors:\n$errs")
