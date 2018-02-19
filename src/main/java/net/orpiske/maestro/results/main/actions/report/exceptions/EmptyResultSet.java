package net.orpiske.maestro.results.main.actions.report.exceptions;

import net.orpiske.maestro.results.dto.Sut;

/**
 * Thrown if no data found for the report being generated. Usually it can be safely ignored
 */
public class EmptyResultSet extends RuntimeException {
    public EmptyResultSet(final Sut sut, final String setName) {
        super("Not enough " + (setName == null ? "" : setName + " ") + "records for " + sut.getSutName() + " " +
                sut.getSutVersion());
    }
}
