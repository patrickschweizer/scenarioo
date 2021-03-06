/* scenarioo-server
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

package org.scenarioo.model.docu.aggregates.scenarios;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.scenarioo.model.docu.entities.Page;
import org.scenarioo.model.docu.entities.StepDescription;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PageSteps {
	
	private Page page;
	
	@XmlElementWrapper(name = "steps")
	@XmlElement(name = "step")
	private List<StepDescription> steps;
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(final Page page) {
		this.page = page;
	}
	
	public List<StepDescription> getSteps() {
		return steps;
	}
	
	public void setSteps(final List<StepDescription> steps) {
		this.steps = steps;
	}
	
	public StepDescription getLastStep() {
		return steps.get(steps.size() - 1);
	}
	
	public int getNumberOfSteps() {
		return steps.size();
	}
	
	public int getIndexOfFirstStep() {
		return steps.get(0).getIndex();
	}
	
}
