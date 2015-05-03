package io.scalac.encog.dsl

import io.scalac.encog.dsl.EncogImplicits.LayersHolder
import io.scalac.encog.dsl.PrimitiveValuesImplicits.{EncogMatrix, EncogCollection}
import org.encog.ml.data.MLDataSet
import org.encog.ml.data.basic.BasicMLDataSet
import org.encog.neural.networks.BasicNetwork
import org.encog.neural.networks.training.propagation.Propagation

object StructureImplicits {

  case class TrainingResult(trainedNetwork: BasicNetwork, trainingSet: BasicMLDataSet)
  case class PropagationFactoryData(network: BasicNetwork, data: MLDataSet)
  type PropagationFactory = PropagationFactoryData => Propagation
  type TrainingFinishCondition = Double => Boolean
  
  implicit class EmptyNeuralNetworkStructure(input: EncogMatrix) {
    def into(layers: LayersHolder) =
      NeuralNetworkStructureWithNetwork(input, layers)
  }

  case class NeuralNetworkStructureWithNetwork(input: EncogMatrix, layers: LayersHolder) {
    def using(propagationFactory: PropagationFactory) =
      NeuralNetworkStructureWithPropagation(input, layers, propagationFactory)
  }

  case class NeuralNetworkStructureWithPropagation(input: EncogMatrix, layers: LayersHolder, propagationFactory: PropagationFactory) {
    def until(finish: TrainingFinishCondition) =
      NeuralNetworkStructureWithFinish(input, layers: LayersHolder, propagationFactory, finish)
  }

  case class NeuralNetworkStructureWithFinish(input: EncogMatrix, layers: LayersHolder, propagationFactory: PropagationFactory, finish: TrainingFinishCondition) {
    def giving(output: EncogCollection[EncogCollection[Double]]) = {
      NeuralNetworkStructure(input, layers, propagationFactory, finish, output)
    }
  }

  case class NeuralNetworkStructure(input: EncogMatrix, layers: LayersHolder, propagationFactory: PropagationFactory, finish: TrainingFinishCondition, output: EncogMatrix) {
    def get(): TrainingResult = {
      val in = input.collect().toArray.map(_.collect().toArray) //TODO: `collect` method could be recursive
      val out = output.collect().toArray.map(_.collect().toArray)

      val network = layers.network()

      val trainingSet = new BasicMLDataSet(in, out)
      val train: Propagation = propagationFactory(PropagationFactoryData(network, trainingSet))
      var epoch = 1
      do {
        train.iteration()
        println("Epoch #" + epoch + " Error:" + train.getError())
        epoch = epoch + 1
      } while( !finish(train.getError()) )

      TrainingResult(network, trainingSet)
    }
  }
}
