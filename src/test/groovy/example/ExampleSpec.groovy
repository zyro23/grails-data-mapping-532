package example

import grails.test.mixin.Mock
import spock.lang.Specification

@Mock([Foo, Bar])
class ExampleSpec extends Specification {

	def "simple prop without alias on left side"() {
		new Foo(name: "foo", fooName: "foo").save()
		new Bar(name: "bar", barName: "bar").save()
		
		when:
		Foo.where {
			def f = Foo
			exists Bar.where {
				name == f.name
			}.id()
		}
		
		then:
		notThrown Exception
	}
	
	def "camelCase prop without alias on left side"() {
		new Foo(name: "foo", fooName: "foo").save()
		new Bar(name: "bar", barName: "bar").save()
		
		when:
		Foo.where {
			def f = Foo
			exists Bar.where {
				barName == f.fooName
			}.id()
		}
		
		then:
		notThrown Exception
	}
	
	def "camelCase prop with alias on left side"() {
		new Foo(fooName: "foo").save()
		new Bar(barName: "bar").save()
		
		when:
		Foo.where {
			def f = Foo
			exists Bar.where {
				def b = Bar
				b.barName == f.fooName
			}.id()
		}
		
		then:
		notThrown Exception
	}
	
}
