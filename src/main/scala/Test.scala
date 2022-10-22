import io.circe._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import model._

import java.io.FileWriter
import scala.io.Source


object Test extends App {

  val conf = new Conf(args)

  val source = Source.fromURL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json").mkString

  val countries: List[Countries] = parser.decode[List[Countries]](source) match {
    case Right(country) => country.filter(_.region == "Africa")
      .sortBy(_.area)(Ordering[Double].reverse)
      .splitAt(10)._1
  }
  val res: List[Export]  = {
    countries.map(c => Export(c.name.official, c.capital.head, c.area))
  }
  val fileName = conf.output.getOrElse("")
  val fileWriter = new FileWriter(fileName)
  fileWriter.write(res.asJson.toString())
  fileWriter.close()

  println(s"JSON $fileName is written successfully")
}