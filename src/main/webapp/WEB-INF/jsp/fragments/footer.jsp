<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--https://getbootstrap.com/docs/4.0/examples/sticky-footer/--%>
<footer class="footer">
    <div class="container">
        <span class="text-muted"><spring:message code="app.footer"/></span>
    </div>
</footer>
<script type="text/javascript">
    const i18n = [];
    i18n["common.deleted"] = '<spring:message code="common.deleted"/>';
    i18n["common.saved"] = '<spring:message code="common.saved"/>';
    i18n["common.enabled"] = '<spring:message code="common.enabled"/>';
    i18n["common.errorStatus"] = '<spring:message code="common.errorStatus"/>';
    i18n["common.confirm"] = '<spring:message code="common.confirm"/>';
    i18n["common.disabled"] = '<spring:message code="common.disabled"/>';
</script>