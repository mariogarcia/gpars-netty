package nettygpars.simple

import javax.xml.stream.*
import javax.xml.namespace.QName
import javax.xml.transform.stream.*

import nettygpars.base.Aggregator
import nettygpars.base.AggregatorException

import static javax.xml.stream.XMLStreamConstants.*

/**
 * This class just reads a xml file and returns the frequency of a given field
 * tag in the file.
**/
class SimpleAggregator implements Aggregator{

	/**
	 *
	**/
	List<Map<String,Integer>> parse(File file){
		def seedMap = new HashMap<String,Integer>()
		def reader = getRecordIteratorFrom(file)
		def nameField = new QName("name")
		def agg = {auxr,acc,ev->
			def isStartElement = ev.eventType == START_ELEMENT
			def isFieldElement = isStartElement && ev.name.localPart == 'field'
			def hasYearField = isFieldElement && 'Year' == ev.getAttributeByName(nameField)?.value
			if (hasYearField){
				def year = auxr.next()?.data?.trim()
				acc[year] = acc.get(year,0) + 1
			}
			acc
		}.curry(reader)
	 /* Collecting year/freq entries */
		reader.inject(seedMap,agg).collect{k,v->[year:k,frequency:v]}	
	}

 	/**
	 * This method returns the event reader
	**/
	def getRecordIteratorFrom(File file){
		XMLInputFactory.newFactory().createXMLEventReader(new FileInputStream(file))	
	}

}
