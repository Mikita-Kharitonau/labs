import org.scalatest.FunSuite
import breeze.linalg._
import com.saio.lab4.tasks.ResourceAllocationTask

class ResourceAllocationTaskTest extends FunSuite {
  test("ResourceAllocationTask.solve0") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 3, 4, 5, 8, 9, 10),
        (0, 2, 3, 7, 9, 12, 13),
        (0, 1, 2, 6, 11, 11, 13)
      )
    )

    assert(rat.solve == (16, List(4, 1, 1)))
  }

  test("ResourceAllocationTask.solve1") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 1, 2, 2, 4, 5, 6),
        (0, 2, 3, 5, 7, 7, 8),
        (0, 2, 4, 5, 6, 7, 7)
      )
    )

    assert(rat.solve == (11, List(2, 4, 0)))
  }

  test("ResourceAllocationTask.solve2") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 1, 1, 3, 6, 10, 11),
        (0, 2, 3, 5, 6, 7, 13),
        (0, 1, 4, 4, 7, 8, 9)
      )
    )

    assert(rat.solve == (13, List(0, 6, 0)))
  }

  test("ResourceAllocationTask.solve3") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 1, 2, 4, 8, 9, 9, 23),
        (0, 2, 4, 6, 6, 8, 10, 11),
        (0, 3, 4, 7, 7, 8, 8, 24)
      )
    )

    assert(rat.solve == (24, List(7, 0, 0)))
  }

  test("ResourceAllocationTask.solve4") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 3, 3, 6, 7, 8, 9, 14),
        (0, 2, 4, 4, 5, 6, 8, 13),
        (0, 1, 1, 2, 3, 3, 10, 11)
      )
    )

    assert(rat.solve == (14, List(0, 0, 7)))
  }

  test("ResourceAllocationTask.solve5") {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 2, 2, 3, 5, 8, 8, 10, 17),
        (0, 1, 2, 5, 8, 10, 11, 13, 15),
        (0, 4, 4, 5, 6, 7, 13, 14, 14),
        (0, 1, 3, 6, 9, 10, 11, 14, 16)
      )
    )

    assert(rat.solve == (18, List(3, 1, 4, 0)))
  }
}
