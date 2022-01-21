package com.danielasfregola.quiz.management.resources

import akka.http.scaladsl.server.Route

import com.danielasfregola.quiz.management.entities.{User, UserUpdate}
import com.danielasfregola.quiz.management.routing.MyResource
import com.danielasfregola.quiz.management.services.CrudService

trait UserResource extends MyResource {

  val crudService: CrudService

  def userRoutes: Route = pathPrefix("users") {
    pathEnd {
      post {
        entity(as[User]) { user =>
          completeWithLocationHeader(
            resourceId = crudService.createUser(user),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
    } ~
    path(Segment) { id =>
      get {
        complete(crudService.getUser(id))
      } ~
      put {
        entity(as[UserUpdate]) { update =>
          complete(crudService.updateUser(id, update))
        }
      } ~
      delete {
        complete(crudService.deleteUser(id))
      }
    }

  }
}


