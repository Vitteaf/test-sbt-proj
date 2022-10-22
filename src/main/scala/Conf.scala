import org.rogach.scallop.ScallopConf

class Conf (args: Seq[String]) extends ScallopConf(args) {
  val output = opt[String](default = Some("result.json"), descr = "Name of an output file")
  verify()
}
