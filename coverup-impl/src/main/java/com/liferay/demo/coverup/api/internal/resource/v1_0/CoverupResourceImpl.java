package com.liferay.demo.coverup.api.internal.resource.v1_0;

import com.liferay.demo.coverup.api.dto.v1_0.CoverupObject;
import com.liferay.demo.coverup.api.dto.v1_0.SuperheroObject;
import com.liferay.demo.coverup.api.resource.v1_0.CoverupResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import javax.validation.constraints.NotNull;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jan Verweij
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/coverup.properties",
	scope = ServiceScope.PROTOTYPE, service = CoverupResource.class
)
public class CoverupResourceImpl extends BaseCoverupResourceImpl {
	String[] superheroes = {"Spiderman", "Superman", "Superwoman", "Ironman", "Hulk", "Magneto", "Vision", "Scarlett Witch","Xavier","Silver Surfer", "Thor", "Doctor Strange", "Jane Strange","Captain Marvel"};

	@Override
	public SuperheroObject getAlive() throws Exception {
		SuperheroObject hero = new SuperheroObject();
		int rnd = new Random().nextInt(superheroes.length);
		hero.setName(superheroes[rnd]);
		hero.setPower(ThreadLocalRandom.current().nextInt(1, 1024 + 1));
		return hero;
	}

	@Override
	public CoverupObject postConfirmation(@NotNull Integer confirmationId) throws Exception {
		return super.postConfirmation(confirmationId);
	}

	@Override
	public CoverupObject postUserConfirmation(@NotNull Integer confirmationId) throws Exception {
		return super.postUserConfirmation(confirmationId);
	}
}