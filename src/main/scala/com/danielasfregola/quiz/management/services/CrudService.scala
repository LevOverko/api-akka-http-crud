package com.danielasfregola.quiz.management.services

import com.danielasfregola.quiz.management.entities.{User, UserUpdate}

import scala.concurrent.{ExecutionContext, Future}

class CrudService(implicit val executionContext: ExecutionContext) {

  var users = Vector.empty[User]

  def createUser(user: User): Future[Option[String]] = Future {
    users.find(_.id == user.id) match {
      case Some(q) => None
      case None =>
        users = users :+ user
        Some(user.id)
    }
  }

  def getUser(id: String): Future[Option[User]] = Future {
    users.find(_.id == id)
  }

  def updateUser(id: String, update: UserUpdate): Future[Option[User]] = {

    def updateEntity(question: User): User = {
      val nameUser = update.nameUser.getOrElse(question.nameUser)
      val ageUser = update.ageUser.getOrElse(question.ageUser)
      User(id, nameUser, ageUser)
    }

    getUser(id).flatMap { maybeUser =>
      maybeUser match {
        case None => Future { None }
        case Some(user) =>
          val updatedUser = updateEntity(user)
          deleteUser(id).flatMap { _ =>
            createUser(updatedUser).map(_ => Some(updatedUser))
          }
      }
    }
  }

  def deleteUser(id: String): Future[Unit] = Future {
    users = users.filterNot(_.id == id)
  }


}

