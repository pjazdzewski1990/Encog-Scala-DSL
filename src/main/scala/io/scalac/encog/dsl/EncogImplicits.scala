package io.scalac.encog.dsl

import io.scalac.encog.dsl.PrimitiveValuesImplicits.Layers
import org.encog.engine.network.activation.{ActivationSigmoid, ActivationFunction}

object EncogImplicits {

  implicit class EnrichedBasicLayer(activation: ActivationFunction) {

    private var hasBias = false
    private var layersCount = Layers(1)

    def having = this
    def having(x: bias.type) = {
      val copy = new EnrichedBasicLayer(activation)
      copy.hasBias = true
      copy.layersCount = this.layersCount
      copy
    }
    def having(layers: Layers) = {
      val copy = new EnrichedBasicLayer(activation)
      copy.hasBias = this.hasBias
      copy.layersCount = layers
      copy
    }

    def +(that: EnrichedBasicLayer):LayersHolder  = LayersHolder(Seq(this, that))
  }

  case class LayersHolder(layers: Seq[EnrichedBasicLayer]) {
    def +(l: EnrichedBasicLayer) = new LayersHolder(layers :+ l)
    def length = layers.length
  }

  case object bias
  def Input = new EnrichedBasicLayer(null)
  def ActivationSigmoid = new EnrichedBasicLayer(new ActivationSigmoid())
}
