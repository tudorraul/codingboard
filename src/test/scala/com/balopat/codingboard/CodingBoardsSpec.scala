package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardsSpec extends Specification {

  val fixture = new {
     val boards = new CodingBoards()
     val lengthOfSessionInMillis = 10000   
     val creationTimeInMillis: Long = 1000
  }


  "CodingBoards" should {

    "initially say that a board does not exist" in {
      fixture.boards.exists("non existent board") should beEqualTo(false)
    }

    "say the board exists after created" in {
      aTestCodingBoard("testCodingBoard")
      fixture.boards.exists("testcodingboard") should beEqualTo(true)
    }

    "can return a board after created" in {
      aTestCodingBoard("testCodingBoard")
      fixture.boards.get("testcodingboard") must beAnInstanceOf[CodingBoard]
    }

    "not return a board when removed" in {
      fixture.boards.remove("testcodingboard")
      fixture.boards.exists("non existent board") should beEqualTo(false) 
    }

    "returns the name of the boards" in {
      aTestCodingBoard("t1")
      aTestCodingBoard("t2")
      aTestCodingBoard("t3")
      
      fixture.boards.list.map(_.board) must contain ("t1", "t2", "t3")
    }
  
    "not allow empty boardname" in {
      fixture.boards.validate("", "1") should beEqualTo(Seq("boardNameError"->"Board name cannot be empty"))    
    }

    "not allow already existing boardname" in {
      fixture.boards.validate("t1", "1") should beEqualTo(Seq("boardNameError"->"Board already exists"))    
    }

    
    "not allow empty lengthOfSession" in {
      fixture.boards.validate("test", "") should beEqualTo(Seq("lengthOfSessionError" -> "Length of session cannot be empty"))
    }


    "not allow non String lengthOfSession" in {
      fixture.boards.validate("test", "non-numeric") should beEqualTo(
            Seq("lengthOfSessionError" -> "Please provide an integer value for length of session!"))
    }

    "not return any error for correct values" in {
      fixture.boards.validate("some baord", "1" ).isEmpty should beTrue 
    }

    "remove the board after expiry" in {
      fixture.boards.create("expiring board", 100, 1000)
      Thread.sleep(101)
      fixture.boards.exists("expiring board") should beFalse
    }

    "coding boards instance should be available" in {
      CodingBoards.instance should not beNull
    }

    def aTestCodingBoard(name: String = "testingCodingBoard")  = { 
        fixture.boards.create(name, fixture.lengthOfSessionInMillis, fixture.creationTimeInMillis)
    }

    def cleanUpBoards() = {
       fixture.boards.list.foreach ( b => {
         fixture.boards.remove(b.board)
        })
    }

  }

}

