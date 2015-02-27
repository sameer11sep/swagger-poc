package com.egencia.sandbox.model

import org.scalatest.FlatSpec

/**
 * Created by samarora on 2/24/15.
 */
class VersionSpec extends FlatSpec{

  "Version" should "be instance of Version" in {
    val hello = new Version("Egencia","1.0.0")
    val result = hello match {
      case f:Version => {
        assertResult("Egencia")(f.getName)
        assertResult("1.0.1")(f.getVersion)

      }
    }
  }

}
