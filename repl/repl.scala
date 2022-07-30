package monkeys
package repl

import java.{util as ju}
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter

class REPL(in: InputStream, out: OutputStream):

  private def prompt = ">> "

  def start =
    val ioScanner = new ju.Scanner(in);
    val writer = new PrintWriter(out)
    try {
      while true do
        writer.print(prompt)
        writer.flush
        val input = ioScanner.nextLine()
        val sc = new scanner.Scanner(s"$input\n")

        var tok = sc.nextToken()
        while !tok.isEOF do
          writer.println(tok)
          tok = sc.nextToken()
        end while
        writer.flush
      end while
    } finally {
      ioScanner.close()
      writer.close()
    }
