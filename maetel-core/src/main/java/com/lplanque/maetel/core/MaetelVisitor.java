package com.lplanque.maetel.core;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

/**
 * TODO
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 */
public final class MaetelVisitor extends SimpleFileVisitor<Path> {
	
	private final Map<String, Function<String, String>> functions;
	private final FilenameFilter filter;
	
	/**
	 * 
	 * @param functions
	 * @param filter
	 */
	public MaetelVisitor(
		Map<String, Function<String, String>> functions, FilenameFilter filter) {
		this.functions = functions;
		this.filter = filter;
	}
	
	/**
	 * 
	 */
	@Override public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) 
		throws IOException {
	
		final File file = path.toFile();
		
		if(!filter.accept(file.getParentFile(), file.getName())) {
			return FileVisitResult.CONTINUE;
		}
		
		try {
			
			final DocumentBuilder builder = newInstance().newDocumentBuilder();
			final Document document = builder.parse(file);
			final XPath xpath = XPathFactory.newInstance().newXPath();

			for (Map.Entry<String, Function<String, String>> mapping : functions.entrySet()) {
				
				final String key = mapping.getKey();
				final Function<String, String> function = mapping.getValue();
				final NodeList nodes = (NodeList)xpath.evaluate(key, document, NODESET);
				final int length = nodes.getLength();

				for (int i = 0; i < length; i++) {
					final Node item = nodes.item(i);
					item.setTextContent(function.apply(item.getTextContent()));
				}
			
				final Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.transform(new DOMSource(document), new StreamResult(file));
			}
		} catch(ParserConfigurationException 
			| SAXException 
			| XPathExpressionException 
			| TransformerFactoryConfigurationError 
			| TransformerException e) { // Don't konw if it is optimal...
			
			throw new IOException("Exception caught inside visitor", e);
		}
		
		return FileVisitResult.CONTINUE;
	}
	
	/**
	 * 
	 * @return
	 */
	public  Map<String, Function<String, String>> getFunctions() {
		return Collections.unmodifiableMap(functions);
	}
	
	/**
	 * 
	 * @return
	 */
	public FilenameFilter getFilter() {
		return filter;
	}
}
