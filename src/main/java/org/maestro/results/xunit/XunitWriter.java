package org.maestro.results.xunit;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.maestro.common.exceptions.MaestroException;
import org.maestro.results.dto.xunit.*;
import org.maestro.results.dto.xunit.Error;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XunitWriter {
    final private Document dom;

    public XunitWriter() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new MaestroException(e);
        }

        dom = db.newDocument();
    }


    private void serializeProblem(final Element eleTestCase, final String elementName, final Problem problem) {
        if (problem == null) {
            return;
        }

        Element element = dom.createElement(elementName);

        element.setAttribute("message", problem.getMessage());
        element.setTextContent(problem.getContent());

        eleTestCase.appendChild(element);
    }


    private void serializeFailure(final Element eleTestCase, final Failure failure) {
        if (failure == null) {
            return;
        }

        serializeProblem(eleTestCase, "failure", failure);
    }

    private void serializeError(final Element eleTestCase, final Error error) {
        if (error == null) {
            return;
        }

        serializeProblem(eleTestCase, "error", error);
    }

    private void serializeTestCases(final Element eleTestSuite, final List<TestCase> testCaseList) {
        for (TestCase testCase : testCaseList) {
            Element eleTestCase = dom.createElement("testcase");

            eleTestCase.setAttribute("assertions", String.valueOf(testCase.getAssertions()));
            eleTestCase.setAttribute("classname", testCase.getClassName());
            eleTestCase.setAttribute("name", testCase.getName());
            eleTestCase.setAttribute("time", String.valueOf(testCase.getTime().getSeconds()));

            serializeFailure(eleTestCase, testCase.getFailure());
            serializeError(eleTestCase, testCase.getError());

            eleTestSuite.appendChild(eleTestCase);
        }
    }

    private void serializeTestSuite(final Element rootEle, final TestSuite testSuite) {
        Element eleTestSuite = dom.createElement("testsuite");

        eleTestSuite.setAttribute("id", testSuite.getId());
        eleTestSuite.setAttribute("name", testSuite.getName());
        eleTestSuite.setAttribute("tests", String.valueOf(testSuite.getTests()));

        serializeTestCases(eleTestSuite, testSuite.getTestCaseList());

        rootEle.appendChild(eleTestSuite);
    }

    private void serializeTestSuites(final Element rootEle, final List<TestSuite> testSuiteList) {
        for (TestSuite testSuite : testSuiteList) {
            serializeTestSuite(rootEle, testSuite);
        }
    }

    public void saveToXML(final File outputFile, TestSuites testSuites) {
        Element rootEle = dom.createElement("testsuites");
        serializeTestSuites(rootEle, testSuites.getTestSuiteList());
        dom.appendChild(rootEle);

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();

            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(outputFile)));

        } catch (IOException | TransformerException te) {
            throw new MaestroException(te);
        }
    }
}
