package com.meow_care.meow_care_service.repositories.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomPostgreSQLDialect extends PostgreSQLDialect {

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        // Get the TypeConfiguration for resolving types
        TypeConfiguration typeConfiguration = functionContributions.getTypeConfiguration();
        BasicTypeRegistry basicTypeRegistry = typeConfiguration.getBasicTypeRegistry();

        functionContributions.getFunctionRegistry().registerPattern(
                "sin", "sin(?1)", basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        );

        functionContributions.getFunctionRegistry().registerPattern(
                "cos", "cos(?1)", basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        );
        functionContributions.getFunctionRegistry().registerPattern(
                "tan", "tan(?1)", basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        );
        functionContributions.getFunctionRegistry().registerPattern(
                "radians", "radians(?1)", basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        );
        functionContributions.getFunctionRegistry().registerPattern(
                "atan2", "atan2(?1, ?2)", basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        );
    }
}
