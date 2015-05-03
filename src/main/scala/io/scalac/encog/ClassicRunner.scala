package io.scalac.encog

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import scala.collection.JavaConversions._

object ClassicRunner {
  def run() = {
    val input = Array(
      Array( 0.0, 0.0 ),
      Array( 1.0, 0.0 ),
      Array( 0.0, 1.0 ),
      Array( 1.0, 1.0 )
    )

    val ideal = Array(
      Array(0.0),
      Array(1.0),
      Array(1.0),
      Array(0.0)
    )

    // create a neural network, without using a factory
    val network = new BasicNetwork();
    network.addLayer(new BasicLayer(null,true,2));
    network.addLayer(new BasicLayer(new ActivationSigmoid(),true,3));
    network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
    network.getStructure().finalizeStructure();
    network.reset();

    // create training data
    val trainingSet = new BasicMLDataSet(input, ideal);

    // train the neural network
    val train = new ResilientPropagation(network, trainingSet);

    var epoch = 1;

    do {
      train.iteration();
      println("Epoch #" + epoch + " Error:" + train.getError());
      epoch = epoch+1;
    } while(train.getError() > 0.01);
    train.finishTraining();

    // test the neural network
    System.out.println("Neural Network Results for Classic:");
    for { pair <- trainingSet.getData() } {
      val output = network.compute(pair.getInput());
      println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
        + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
    }

    Encog.getInstance().shutdown();
  }
}
