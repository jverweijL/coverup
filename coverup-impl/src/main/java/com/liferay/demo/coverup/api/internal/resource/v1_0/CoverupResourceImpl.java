package com.liferay.demo.coverup.api.internal.resource.v1_0;

import com.liferay.demo.coverup.api.dto.v1_0.CoverupObject;
import com.liferay.demo.coverup.api.dto.v1_0.SuperheroObject;
import com.liferay.demo.coverup.api.resource.v1_0.CoverupResource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
	String[] SUPERHEROES = {"Spiderman", "Superman", "Superwoman", "Ironman", "Hulk", "Magneto", "Vision", "Scarlett Witch","Xavier","Silver Surfer", "Thor", "Doctor Strange", "Jane Strange","Captain Marvel"};

	@Override
	public SuperheroObject getAlive() throws Exception {
		SuperheroObject hero = new SuperheroObject();
		int rnd = new Random().nextInt(SUPERHEROES.length);
		hero.setName(SUPERHEROES[rnd]);
		hero.setPower(ThreadLocalRandom.current().nextInt(1, 1024 + 1));
		return hero;
	}

	@Override
	public CoverupObject postConfirmation(@NotNull Integer confirmationId) throws Exception {
		return super.postConfirmation(confirmationId);
	}

	@Override
	public CoverupObject postUserConfirmation(@NotNull Integer confirmationId) throws Exception {
		long roleId = _roleLocalService.getRole(contextUser.getCompanyId(),"Administrator").getRoleId();

		List<User> users = _userLocalService.getUsers(0,10);

		for (User user:users) {
			System.out.println("u:"+user.getScreenName());
			for (Role r:user.getRoles()){
				System.out.println("r:"+r.getName());
			}
			if (!_roleLocalService.hasUserRole(user.getUserId(),roleId))
			{
				user.setFirstName(getScrambled(user.getFirstName()));
				user.setLastName(getScrambled(user.getLastName()));
			}
		}

		CoverupObject c = new CoverupObject();
		c.setId(1);
		c.setName("done");
		return c;
	}

	private String getScrambled(String original) {
		//todo make sure screenname is unique
		//todo create alpahbet long enough string
		List<String> ALPHABET = Arrays.asList("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz".split(""));

		Collections.shuffle(ALPHABET);

		StringBuilder sb = new StringBuilder(original.length());
		for (int i = 0;i<=original.length();i++) {
			sb.append(ALPHABET.get(i));
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
			CoverupResourceImpl.class);

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}