$(document)
		.ready(
				function() {
					$(document)
							.keydown(
									function(event) {
										if (event.ctrlKey == true
												&& (event.which == '118' || event.which == '86')) {
											event.preventDefault();
										}
									});

					$saveButton = $("form#change-form input[type='submit']");
					$tokenField = $("form#change-form input[name='token']");
					$passwordField = $("form#change-form input[name='newPassword']");
					$retryPasswordField = $("form#change-form input[name='retryNewPassword']")
					$passwordGroup = $('div.password-group');
					$retryGroup = $('div.retry-group');

					var passwordValidation = function() {
						var lengthOfPassword = this.value.length;

						if (lengthOfPassword < 6) {
							$("p.to-short").removeClass("hide");
							$("p.to-long").addClass("hide");
							$passwordGroup.addClass("has-error");
							$saveButton.prop("disabled", true);
						}

						if (lengthOfPassword == 0
								|| (lengthOfPassword > 5 && lengthOfPassword < 9)) {
							$("p.to-long").addClass("hide");
							$("p.to-short").addClass("hide");
							$passwordGroup.removeClass("has-error");
							$saveButton.prop("disabled", false);
						}

						if (lengthOfPassword > 8) {
							$("p.to-long").removeClass("hide");
							$("p.to-short").addClass("hide");
							$passwordGroup.addClass("has-error");
							$saveButton.prop("disabled", true);
						}

						var retryText = $retryPasswordField.val();
						if (retryText !== this.value) {
							$("p.diff-input").removeClass("hide");
							$retryGroup.addClass("has-error");
							$saveButton.prop("disabled", true);
						} else {
							$("p.diff-input").addClass("hide");
							$retryGroup.removeClass("has-error");
							$saveButton.prop("disabled", false);
						}
					}

					var samePasswordValidation = function() {
						var passwordFieldText = $passwordField.val();
						var retryPasswordText = this.value;

						if (passwordFieldText !== retryPasswordText) {
							$("p.diff-input").removeClass("hide");
							$retryGroup.addClass("has-error");
							$saveButton.prop("disabled", true);
						} else {
							$("p.diff-input").addClass("hide");
							$retryGroup.removeClass("has-error");
							$saveButton.prop("disabled", false);
						}
					};

					var url = "/fredapi/account/password";

					var postNewPassword = function() {
						var data = {
							token : $tokenField.val(),
							newPassword : $passwordField.val()
						};
						$.ajax({
							   type: "POST",
							   url: url,
							   data: JSON.stringify(data),
							   contentType: "application/json",
							   dataType: 'json',
							   complete: function(data) {
							       alert(data);
							  }
						});
					};

					$("#change-form").submit(function(event) {
						event.preventDefault();
					});
					
					$saveButton.click(function(){
						postNewPassword();
					});

					$passwordField.keyup(passwordValidation).blur(
							passwordValidation);
					$retryPasswordField.keyup(samePasswordValidation).blur(
							samePasswordValidation);
				});