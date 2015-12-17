package io.github.jfrazee.nifi.processors.aws.kinesis

import java.io._

// ScalaTest
import org.scalatest._

// NiFi
import org.apache.nifi.util.{ TestRunner, TestRunners }

class PutKinesisRecordSpec extends FunSpec {
  describe("PutKinesisRecord") {
    it("should successfully transfer a FlowFile") {
      val runner = TestRunners.newTestRunner(new PutKinesisRecord)
      val content = new ByteArrayInputStream("{}".getBytes)
      runner.enqueue(content)
      runner.run(1)
      runner.assertAllFlowFilesTransferred(REL_SUCCESS, 1)
    }
  }
}
