import com.saio.lab1.methods.DualSimplexMethod
import org.scalatest.FunSuite
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.abs

class DualSimplexMethodTest extends FunSuite {

  def assertHelper(actual: Either[String, DenseVector[Double]], expected: DenseVector[Double], diff: Double): Boolean = {
    actual match {
      case Left(value) => println(value); false
      case Right(value) => (abs(value - expected) <:< DenseVector.fill(expected.length){diff}).forall(b => b)
    }
  }

  test("DualSimplexMethod.solve0") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (2.0, 1.0, -1.0, 0.0, 0.0, 1.0),
        (1.0, 0.0, 1.0, 1.0, 0.0, 0.0),
        (0.0, 1.0, 0.0, 0.0, 1.0, 0.0)
      ),
      DenseVector(2.0, 5.0, 0.0),
      DenseVector(3.0, 2.0, 0.0, 3.0, -2.0, -4.0),
      DenseVector(0.0, -1.0, 2.0, 1.0, -1.0, 0.0),
      DenseVector(2.0, 4.0, 4.0, 3.0, 3.0, 5.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](1.5, 1, 2, 1.5, -1, 0), 0.001))
  }

  test("DualSimplexMethod.solve1") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, -5.0, 3.0, 1.0, 0.0, 0.0),
        (4.0, -1.0, 1.0, 0.0, 1.0, 0.0),
        (2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
      ),
      DenseVector(-7.0, 22.0, 30.0),
      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
      DenseVector(2.0, 1.0, 0.0, 0.0, 1.0, 1.0),
      DenseVector(6.0, 6.0, 5.0, 2.0, 4.0, 6.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](5, 3, 1, 0, 4, 6), 0.001))
  }

  test("DualSimplexMethod.solve2") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, 0.0, 2.0, 2.0, -3.0, 3.0),
        (0.0, 1.0, 0.0, -1.0, 0.0, 1.0),
        (1.0, 0.0, 1.0, 3.0, 2.0, 1.0)
      ),
      DenseVector(15.0, 0.0, 13.0),
      DenseVector(3.0, 0.5, 4.0, 4.0, 1.0, 5.0),
      DenseVector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      DenseVector(3.0, 5.0, 4.0, 3.0, 3.0, 4.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](3, 0, 4, 1.1818, 0.6364, 1.1818), 0.001))
  }

  test("DualSimplexMethod.solve3") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, 0.0, 0.0, 12.0, 1.0, -3.0, 4.0, -1.0),
        (0.0, 1.0, 0.0, 11.0, 12.0, 3.0, 5.0, 3.0),
        (0.0, 0.0, 1.0, 1.0, 0.0, 22.0, -2.0, 1.0)
      ),
      DenseVector(40.0, 107.0, 61.0),
      DenseVector(2.0, 1.0, -2.0, -1.0, 4.0, -5.0, 5.0, 5.0),
      DenseVector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      DenseVector(3.0, 5.0, 5.0, 3.0, 4.0, 5.0, 6.0, 3.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](3, 5, 0, 1.8779, 2.7545, 3.0965, 6, 3), 0.001))
  }

  test("DualSimplexMethod.solve4") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, -3.0, 2.0, 0.0, 1.0, -1.0, 4.0, -1.0, 0.0),
        (1.0, -1.0, 6.0, 1.0, 0.0, -2.0, 2.0, 2.0, 0.0),
        (2.0, 2.0, -1.0, 1.0, 0.0, -3.0, 8.0, -1.0, 1.0),
        (4.0, 1.0, 0.0, 0.0, 1.0, -1.0, 0.0, -1.0, 1.0),
        (1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
      ),
      DenseVector(3.0, 9.0, 9.0, 5.0, 9.0),
      DenseVector(-1.0, 5.0, -2.0, 4.0, 3.0, 1.0, 2.0, 8.0, 3.0),
      DenseVector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
      DenseVector(5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](1.1579, 0.6942, 0, 0, 2.8797, 0, 1.0627, 3.2055, 0), 0.001))
  }

  test("DualSimplexMethod.solve8") {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, 3.0, 1.0, -1.0, 0.0, -3.0, 2.0, 1.0),
        (2.0, 1.0, 3.0, -1.0, 1.0, 4.0, 1.0, 1.0),
        (-1.0, 0.0, 2.0, -2.0, 2.0, 1.0, 1.0, 1.0)
      ),
      DenseVector(4.0, 12.0, 4.0),
      DenseVector(2.0, -1.0, 2.0, 3.0, -2.0, 3.0, 4.0, 1.0),
      DenseVector(-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0),
      DenseVector(2.0, 3.0, 1.0, 4.0, 3.0, 2.0, 4.0, 4.0)
    )

    assert(assertHelper(dsm.solve, DenseVector[Double](-1.0, 0.4074, 1, 4, -0.3704, 1.7407, 4, 4), 0.001))
  }
}
