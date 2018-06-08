package org.maestro.results.main.actions.report.exceptions;

import org.maestro.results.dto.Sut;

/**
 * Thrown if no data found for the report being generated. Usually it can be safely ignored
 */
public class EmptyResultSet extends RuntimeException {
    public EmptyResultSet(final Sut sut, final String setName) {
        super("Not enough " + (setName == null ? "" : setName + " ") + "records for " + sut.getSutName() + " " +
                sut.getSutVersion());
    }
}
