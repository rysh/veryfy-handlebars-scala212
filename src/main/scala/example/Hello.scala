package example

import com.github.jknack.handlebars._
import com.github.jknack.handlebars.{ValueResolver, Handlebars}

object Hello extends Greeting with App {
}

trait Greeting {
  def execute(): String = {
    val handlebars = new Handlebars

    val template = handlebars.compileInline("{{fuga.name}}")
    val resolvers = ValueResolver.VALUE_RESOLVERS ++ Array(
      CaseClassValueResolver,
      JsonNodeValueResolver.INSTANCE)

    val context = Context
      .newBuilder(Hoge(Fuga("Scala"), "hoge"))
      .resolver(resolvers:_*)
      .build()
    template.apply(context)
  }
}

case class Hoge(
 fuga: Fuga,
 doubt: String
) {
   lazy val applicationIdAsID: Long = 0L
  //val applicationIdAsID: Long = 0L // lazyを削ると2.12でも動作する
}
case class Fuga(name: String)
import java.util.{Set => jSet, Map => jMap}
import scala.collection.JavaConverters._

trait OptionResolvable {

  def flattenOpt(value:AnyRef): AnyRef =
    value match {
      case Some(null) => ""
      case Some(v) => v.asInstanceOf[AnyRef]
      case None => ""
      case v => v
    }

}


object CaseClassValueResolver extends ValueResolver with OptionResolvable {

  override def resolve(context: scala.Any, name: String): AnyRef = {
    context match {
      case product:Product =>
        productAsMap(product)
          .get(name)
          .map(v => flattenOpt(v.asInstanceOf[AnyRef]))
          .getOrElse(ValueResolver.UNRESOLVED)
      case _=> ValueResolver.UNRESOLVED
    }
  }


  override def resolve(context: scala.Any): AnyRef = {
    ValueResolver.UNRESOLVED
  }


  override def propertySet(context: scala.Any): jSet[jMap.Entry[String, AnyRef]] = {
    context match {
      case product:Product =>
        productAsMap(product)
          .asJava
          .entrySet().asInstanceOf[jSet[jMap.Entry[String, AnyRef]]]
      case _=> java.util.Collections.emptySet()
    }
  }


  private def productAsMap(product:Product): Map[String, Any] =
    product.getClass.getDeclaredFields
      .map(_.getName)
      .zip(product.productIterator.toList).toMap

}


