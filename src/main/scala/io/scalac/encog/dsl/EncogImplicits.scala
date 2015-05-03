package io.scalac.encog.dsl

import io.scalac.encog.dsl.PrimitiveValuesImplicits.Layers
import org.encog.engine.network.activation.{ActivationSigmoid, ActivationFunction}
import org.encog.neural.networks.layers.{BasicLayer, Layer}
import org.encog.neural.networks.{BasicNetwork, ContainsFlat}

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

    def layer: Layer =
      new BasicLayer(activation, hasBias, layersCount.num)
  }

  case class LayersHolder(layers: Seq[EnrichedBasicLayer]) {
    def +(l: EnrichedBasicLayer) = new LayersHolder(layers :+ l)
    def length = layers.length

    def network(): BasicNetwork = {
      val network = new BasicNetwork()

      layers.foreach(l => {
        network.addLayer(l.layer)
      })

      network.getStructure().finalizeStructure()
      network.reset()
      network
    }
  }

  case object bias
  def InputLayer = new EnrichedBasicLayer(null)
  def ActivationSigmoid = new EnrichedBasicLayer(new ActivationSigmoid())
}
