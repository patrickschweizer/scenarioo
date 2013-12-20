/* scenarioo-api
 * Copyright (C) 2014, scenarioo.org Development Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.scenarioo.api.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8246746281046270932L;
	@Getter
	private final String resource;
	
	public ResourceNotFoundException(final String resource, final Throwable throwable) {
		super("Resource not found: " + resource, throwable);
		this.resource = resource;
	}
	
	public ResourceNotFoundException(final String resource) {
		super("Resource not found: " + resource);
		this.resource = resource;
	}
	
}
