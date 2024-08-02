package dataGenerators

trait CustomGenerator [GENERATOR_TYPE] {
  protected def generate(): GENERATOR_TYPE
}
