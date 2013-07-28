package org.castafiore.security.api;

import java.io.Serializable;
import java.util.Collection;

import org.castafiore.security.sessions.LoggedSession;

public interface SessionManager extends Serializable{
	public Collection<LoggedSession> getLoggedSessions();
}
