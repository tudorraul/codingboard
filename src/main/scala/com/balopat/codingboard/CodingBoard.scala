package com.balopat.codingboard


case class CodingBoard (board: String, lengthOfSessionInMillis: Long, creationTimeInMillis: Long) {
  var codeSnippets = List[CodeSnippet]()

  def removeSnippetById(snippetId: String): Unit = {
    codeSnippets = codeSnippets.filter(_.id != snippetId)
  }

  def += (codeSnippet : CodeSnippet): CodingBoard = {
    if (codeSnippets.filter( _.id.equals(codeSnippet.id)).isEmpty) 
      codeSnippets :+= codeSnippet
     this
  }

  override def toString = board
  
  def lastCodeSnippetId = if (codeSnippets.isEmpty)  "N/A" else codeSnippets.last.id

  def timeLeftInSeconds(now: Long = System.currentTimeMillis): Long = {
    ((lengthOfSessionInMillis) - (now - creationTimeInMillis))/1000
  }

  def url: String = {
    board.toLowerCase.replaceAll("[^a-z|0-9]","_")
  }
}
