package com.cleanarchitecture.infrastructure.persistence.mapper;

import com.cleanarchitecture.domain.entity.User;
import com.cleanarchitecture.infrastructure.persistence.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "version", ignore = true)
    UserJpaEntity toJpaEntity(User user);

    User toDomainEntity(UserJpaEntity jpaEntity);
}
