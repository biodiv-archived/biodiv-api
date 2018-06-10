package biodiv.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

import org.glassfish.jersey.server.validation.ValidationError;

import jersey.repackaged.com.google.common.base.Function;
import jersey.repackaged.com.google.common.collect.Lists;

public class ValidationHelper {
	  /**
     * Extract {@link ConstraintViolation constraint violations} from given exception and transform them into a list of
     * {@link ValidationError validation errors}.
     *
     * @param violation exception containing constraint violations.
     * @return list of validation errors (not {@code null}).
     */
    public static List<ValidationError> constraintViolationToValidationErrors(final ConstraintViolationException violation) {
        return Lists.transform(Lists.newArrayList(violation.getConstraintViolations()),
                new Function<ConstraintViolation<?>, ValidationError>() {

                    @Override
                    public ValidationError apply(final ConstraintViolation<?> violation) {
                        return new ValidationError(
                                violation.getMessage(),
                                violation.getMessageTemplate(),
                                getViolationPath(violation),
                                getViolationInvalidValue(violation.getInvalidValue())
                        );
                    }
                });
    }

    /**
     * Provide a string value of (invalid) value that caused the exception.
     *
     * @param invalidValue invalid value causing BV exception.
     * @return string value of given object or {@code null}.
     */
    private static String getViolationInvalidValue(final Object invalidValue) {
        if (invalidValue == null) {
            return null;
        }

        if (invalidValue.getClass().isArray()) {
            if (invalidValue instanceof Object[]) {
                return Arrays.toString((Object[]) invalidValue);
            } else if (invalidValue instanceof boolean[]) {
                return Arrays.toString((boolean[]) invalidValue);
            } else if (invalidValue instanceof byte[]) {
                return Arrays.toString((byte[]) invalidValue);
            } else if (invalidValue instanceof char[]) {
                return Arrays.toString((char[]) invalidValue);
            } else if (invalidValue instanceof double[]) {
                return Arrays.toString((double[]) invalidValue);
            } else if (invalidValue instanceof float[]) {
                return Arrays.toString((float[]) invalidValue);
            } else if (invalidValue instanceof int[]) {
                return Arrays.toString((int[]) invalidValue);
            } else if (invalidValue instanceof long[]) {
                return Arrays.toString((long[]) invalidValue);
            } else if (invalidValue instanceof short[]) {
                return Arrays.toString((short[]) invalidValue);
            }
        }

        return invalidValue.toString();
    }

    /**
     * Get a path to a field causing constraint violations.
     *
     * @param violation constraint violation.
     * @return path to a property that caused constraint violations.
     */
    private static String getViolationPath(final ConstraintViolation violation) {
        final String rootBeanName = violation.getRootBean().getClass().getSimpleName();
        final String propertyPath = violation.getPropertyPath().toString();

        //return rootBeanName + (!"".equals(propertyPath) ? '.' + propertyPath : "");
        return propertyPath;
    }

    /**
     * Determine the response status (400 or 500) from the given BV exception.
     *
     * @param violation BV exception.
     * @return response status (400 or 500).
     */
    public static Response.Status getResponseStatus(final ConstraintViolationException violation) {
        final Iterator<ConstraintViolation<?>> iterator = violation.getConstraintViolations().iterator();

        if (iterator.hasNext()) {
            for (final Path.Node node : iterator.next().getPropertyPath()) {
                final ElementKind kind = node.getKind();

                if (ElementKind.RETURN_VALUE.equals(kind)) {
                    return Response.Status.INTERNAL_SERVER_ERROR;
                }
            }
        }

        return Response.Status.BAD_REQUEST;
    }

    /**
     * Prevent instantiation.
     */
    private ValidationHelper() {
    }
}
