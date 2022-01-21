package com.danielasfregola.quiz.management

import scala.concurrent.ExecutionContext

import akka.http.scaladsl.server.Route

import com.danielasfregola.quiz.management.resources.UserResource
import com.danielasfregola.quiz.management.services.CrudService

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val crudService = new CrudService

  val routes: Route = userRoutes

}

trait Resources extends UserResource

