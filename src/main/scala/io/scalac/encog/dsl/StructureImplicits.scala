package io.scalac.encog.dsl

import io.scalac.encog.dsl.EncogImplicits.LayersHolder
import org.encog.ml.data.MLDataSet
import org.encog.ml.data.basic.BasicMLDataSet
import org.encog.neural.networks.ContainsFlat
import org.encog.neural.networks.training.propagation.Propagation

object StructureImplicits {

  case class PropagationFactoryData(network: ContainsFlat, data: MLDataSet)
  type PropagationFactory = PropagationFactoryData => Propagation
  type TrainingFinishCondition = Double => Boolean
  
  implicit class EmptyNeuralNetworkStructure(input: Array[Array[Double]]) {
    def into(layers: LayersHolder) =
      NeuralNetworkStructureWithNetwork(input, layers)
  }

  case class NeuralNetworkStructureWithNetwork(input: Array[Array[Double]], layers: LayersHolder) {
    def using(propagationFactory: PropagationFactory) =
      NeuralNetworkStructureWithPropagation(input, layers, propagationFactory)
  }

  case class NeuralNetworkStructureWithPropagation(input: Array[Array[Double]], layers: LayersHolder, propagationFactory: PropagationFactory) {
    def until(finish: TrainingFinishCondition) =
      NeuralNetworkStructureWithFinish(input, layers: LayersHolder, propagationFactory, finish)
  }

  case class NeuralNetworkStructureWithFinish(input: Array[Array[Double]], layers: LayersHolder, propagationFactory: PropagationFactory, finish: TrainingFinishCondition) {
    def giving(output: Array[Array[Double]]) = {
      NeuralNetworkStructure(input, layers, propagationFactory, finish, output)
    }
  }

  case class NeuralNetworkStructure(input: Array[Array[Double]], holder: LayersHolder, propagationFactory: PropagationFactory, finish: TrainingFinishCondition, output: Array[Array[Double]]) {
    def get(): MLDataSet = {
      val trainingSet = new BasicMLDataSet(input, output)
      val train: Propagation = propagationFactory(PropagationFactoryData(holder.network, trainingSet))
      var epoch = 1
      do {
        train.iteration()
        println("Epoch #" + epoch + " Error:" + train.getError())
        epoch = epoch + 1
      } while( !finish(train.getError()) )
      trainingSet
    }
  }
}
