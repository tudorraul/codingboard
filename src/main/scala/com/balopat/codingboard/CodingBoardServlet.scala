package com.balopat.codingboard

class CodingBoardServlet(boards: CodingBoards = CodingBoards.instance) extends CodingBoardViewHelper {

  get("/") {
    index()
  }

  post("/submitboard") {
    createAndJoinBoard(params("board"), params("lengthOfSessionInMinutes"))
  }

  get("/boards/:board") {
    joinCodingBoard(params("board"))
  }

  get("/createboard") {
    contentType = "text/html"
    findTemplate(requestPath) map {
      path =>
        layoutTemplate(path)
    }
  }

  get("/boards/:board/post") {
    joinCodingBoard(params("board"))
  }

  post("/boards/:board/post") {
    postSnippetToBoard(params("board"), params("formtoken"), params("description"), params("code"), params("language"))
    redirect("/boards/" + params("board"))
  }

  post("/boards/:board/refresh") {
    isBoardChangedSinceLastCodeSnippet(params("board"), params("lastCodeSnippetId"))
  }

  get("/boards/:board/codesnippet") {
    codeSnippetFormFor(params("board"))
  }

  get("/boards/:board/codesnippet/:snippetId/delete") {
    deleteSnippetFromBoard(params("board"), params("snippetId"))
    redirect("/boards/" + params("board"))
  }

  notFound {
    serveStaticResource() getOrElse index()
  }
}
