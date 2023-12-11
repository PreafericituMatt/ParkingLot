<%--
  Created by IntelliJ IDEA.
  User: Stefan
  Date: 12/3/2023
  Time: 8:41 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Add Car">
<h2>Add Car</h2>

<form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/AddCar">

    <!-- License Plate -->
    <div class="row">
        <div class="col-md-6 mb-3">
            <label for="license_plate">License Plate</label>
            <input type="text" class="form-control" id="license_plate" name="license_plate" placeholder="" value="" required>
            <div class="invalid-feedback">
                License Plate is required.
            </div>
        </div>
    </div>

    <!-- Parking Spot -->
    <div class="row">
        <div class="col-md-6 mb-3">
            <label for="parking_spot">Parking Spot</label>
            <input type="text" class="form-control" id="parking_spot" name="parking_spot" placeholder="" value="" required>
            <div class="invalid-feedback">
                Parking Spot is required.
            </div>
        </div>
    </div>

    <!-- Owner Dropdown -->
    <div class="row">
        <div class="col-md-6 mb-3">
            <label for="owner">Owner</label>
            <select class="form-control" id="owner" name="owner_id" required>
                <option value="">Choose...</option>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <option value="${user.id}">${user.username}</option>
                </c:forEach>
            </select>
            <div class="invalid-feedback">
                Please select an owner.
            </div>
        </div>
    </div>

    <!-- Save Button -->
    <button class="w-100 btn btn-primary btn-lg" type="submit">Save</button>
</form>

</t:pageTemplate>