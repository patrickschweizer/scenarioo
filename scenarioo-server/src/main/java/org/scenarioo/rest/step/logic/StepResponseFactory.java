package org.scenarioo.rest.step.logic;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.scenarioo.api.ScenarioDocuReader;
import org.scenarioo.business.aggregator.PageNameSanitizer;
import org.scenarioo.dao.aggregates.AggregatedDocuDataReader;
import org.scenarioo.model.docu.aggregates.steps.StepNavigation;
import org.scenarioo.model.docu.aggregates.steps.StepStatistics;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.model.docu.entities.UseCase;
import org.scenarioo.rest.base.BuildIdentifier;
import org.scenarioo.rest.base.StepIdentifier;
import org.scenarioo.rest.step.dto.StepDetails;

public class StepResponseFactory {
	
	private final AggregatedDocuDataReader aggregatedDataReader;
	
	private final ScenarioDocuReader scenarioDocuReader;
	
	public StepResponseFactory(final AggregatedDocuDataReader aggregatedDataReader,
			final ScenarioDocuReader scenarioDocuReader) {
		this.aggregatedDataReader = aggregatedDataReader;
		this.scenarioDocuReader = scenarioDocuReader;
	}
	
	public Response createResponse(final StepLoaderResult stepLoaderResult, final StepIdentifier stepIdentifier,
			final BuildIdentifier buildIdentifierBeforeAliasResolution, final boolean fallback) {
		if (stepLoaderResult.isRequestedStepFound()) {
			return foundStepResponse(stepLoaderResult, stepIdentifier, fallback, buildIdentifierBeforeAliasResolution);
		} else if (stepLoaderResult.isRedirect()) {
			return redirectResponse(stepLoaderResult, buildIdentifierBeforeAliasResolution);
		} else {
			return notFoundResponse();
		}
	}
	
	private Response foundStepResponse(final StepLoaderResult stepLoaderResult, final StepIdentifier stepIdentifier,
			final boolean fallback, final BuildIdentifier buildIdentifierBeforeAliasResolution) {
		StepDetails stepDetails = getStepDetails(stepLoaderResult, stepIdentifier, fallback,
				buildIdentifierBeforeAliasResolution);
		return Response.ok(stepDetails).build();
	}
	
	private StepDetails getStepDetails(final StepLoaderResult stepLoaderResult, final StepIdentifier stepIdentifier,
			final boolean fallback, final BuildIdentifier buildIdentifierBeforeAliasResolution) {
		Step step = scenarioDocuReader.loadStep(stepIdentifier.getBranchName(), stepIdentifier.getBuildName(),
				stepIdentifier.getUsecaseName(), stepIdentifier.getScenarioName(), stepLoaderResult.getStepIndex());
		PageNameSanitizer.sanitizePageName(step);
		
		StepNavigation navigation = aggregatedDataReader.loadStepNavigation(stepIdentifier.getScenarioIdentifier(),
				stepLoaderResult.getStepIndex());
		StepStatistics statistics = stepLoaderResult.getStepStatistics();
		
		Scenario scenario = scenarioDocuReader.loadScenario(stepIdentifier.getBranchName(),
				stepIdentifier.getBuildName(), stepIdentifier.getUsecaseName(), stepIdentifier.getScenarioName());
		UseCase usecase = scenarioDocuReader.loadUsecase(stepIdentifier.getBranchName(), stepIdentifier.getBuildName(),
				stepIdentifier.getUsecaseName());
		
		StepIdentifier stepIdentifierWithPotentialAlias = stepIdentifier
				.withDifferentBuildIdentifier(buildIdentifierBeforeAliasResolution);
		return new StepDetails(stepIdentifierWithPotentialAlias, fallback, step, navigation, statistics,
				usecase.getLabels(), scenario.getLabels());
	}
	
	private Response redirectResponse(final StepLoaderResult stepImage,
			final BuildIdentifier buildIdentifierBeforeAliasResolution) {
		StepIdentifier stepIdentifier = stepImage.getStepIdentifier();
		StepIdentifier stepIdentifierWithPotentialAlias = stepIdentifier
				.withDifferentBuildIdentifier(buildIdentifierBeforeAliasResolution);
		return Response.temporaryRedirect(stepIdentifierWithPotentialAlias.getStepUriForRedirect()).build();
	}
	
	private Response notFoundResponse() {
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
