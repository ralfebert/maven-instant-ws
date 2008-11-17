<?xml version="1.0" encoding="UTF-8"?>
<definitions 
	name="$name$" 
	targetNamespace="$serviceNamespace$"
	xmlns="http://schemas.xmlsoap.org/wsdl/"
	xmlns:tns="$serviceNamespace$"
	xmlns:$typesNamespacePrefix$="$typesNamespace$"
	xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/">
	<types>
		<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
			<xsd:import namespace="$typesNamespace$"
				schemaLocation="$schemaFileName$" />
		</xsd:schema>
	</types>
$operations:{operation|
	<message name="$operation.name$RequestMessage">
		<part name="body" element="$typesNamespacePrefix$:$operation.name$Request" />
	</message>
	<message name="$operation.name$ResponseMessage">
		<part name="body" element="$typesNamespacePrefix$:$operation.name$Response" />
	</message>
	$if(operation.hasFault)$
	<message name="$operation.name$FaultMessage">
		<part name="body" element="$typesNamespacePrefix$:$operation.name$Fault" />
	</message>	
	$endif$ 	
}; separator="\n"$	
	<portType name="$servicePrefix$PortType">
$operations:{operation|	
		<operation name="$operation.name$">
			<input message="tns:$operation.name$RequestMessage" />
			<output message="tns:$operation.name$ResponseMessage" />
			$if(operation.hasFault)$
			<fault name="$operation.name$Fault" message="tns:$operation.name$FaultMessage" />
			$endif$
		</operation>
}; separator="\n"$
	</portType>
	<binding name="$servicePrefix$SoapBinding" type="tns:$servicePrefix$PortType">
		<soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http" />
$operations:{operation|		
		<operation name="$operation.name$">
			<soap:operation soapAction="$operation.name$" />
			<input>
				<soap:body use="literal" />
			</input>
			<output>
				<soap:body use="literal" />
			</output>
			$if(operation.hasFault)$
			<fault name="$operation.name$Fault">
				<soap:fault name="$operation.name$Fault" use="literal" />
			</fault>
			$endif$
		</operation>
}; separator="\n"$		
	</binding>
	<service name="$servicePrefix$Service">
		<port name="$servicePrefix$Port" binding="tns:$servicePrefix$SoapBinding">
			<soap:address location="TODO" />
		</port>
	</service>
</definitions>