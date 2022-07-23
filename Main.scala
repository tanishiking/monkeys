package monkeys

@main def main =
  val loop = new repl.REPL(System.in, System.out)
  loop.start
