import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.abs
import org.scalatest.FunSuite
import com.saio.lab2.methods.BnBMethod


class BnBMethodTest extends FunSuite {

  def assertHelper(
                    actual: Either[String, DenseVector[Int]],
                    expected: DenseVector[Int]) = {
    actual match {
      case Left(value) => println(value); fail()
      case Right(value) => assert(value == expected)
    }
  }

  test("BnBMethod.solve0") {
    val bnb: BnBMethod = new BnBMethod(
      DenseMatrix(
        (1.0, -5.0, 3.0, 1.0, 0.0, 0.0),
        (4.0, -1.0, 1.0, 0.0, 1.0, 0.0),
        (2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
      ),
      DenseVector(-8.0, 22.0, 30.0),
      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
      DenseVector(2.0, 1.0, 0.0, 0.0, 1.0, 1.0),
      DenseVector(6.0, 6.0, 5.0, 2.0, 4.0, 6.0)
    )

    assertHelper(bnb.solve, DenseVector[Int](6, 3, 0, 1, 1, 6))
  }

  test("BnBMethod.solve1") {
    val bnb: BnBMethod = new BnBMethod(
      DenseMatrix(
        (1.0, 0.0, 3.0, 1.0, 0.0, 0.0),
        (0.0, -1.0, 1.0, 1.0, 1.0, 2.0),
        (-2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
      ),
      DenseVector(10.0, 8.0, 10.0),
      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
      DenseVector(0.0, 1.0, -1.0, 0.0, -2.0, 1.0),
      DenseVector(3.0, 3.0, 6.0, 2.0, 4.0, 6.0)
    )

    assertHelper(bnb.solve, DenseVector[Int](1, 1, 3, 0, 2, 2))
  }

  test("BnBMethod.solve2") {
    val bnb: BnBMethod = new BnBMethod(
      DenseMatrix(
        (1.0, 0.0, 1.0, 0.0, 0.0, 1.0),
        (1.0, 2.0, -1.0, 1.0, 1.0, 2.0),
        (-2.0, 4.0, 1.0, 0.0, 1.0, 0.0)
      ),
      DenseVector(-3.0, 3.0, 13.0),
      DenseVector(-3.0, 2.0, 0.0, -2.0, -5.0, 2.0),
      DenseVector(-2.0, -1.0, -2.0, 0.0, 1.0, -4.0),
      DenseVector(2.0, 3.0, 1.0, 5.0, 4.0, -1.0)
    )

    assertHelper(bnb.solve, DenseVector[Int](-2, 2, 0, 2, 1, -1))
  }

  test("BnBMethod.solve3") {
    val bnb: BnBMethod = new BnBMethod(
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

    assertHelper(bnb.solve, DenseVector[Int](1, 1, 2, 2, 3, 3, 6, 3))
  }

}
