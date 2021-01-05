$(function() {
    $('#submitBtn').click(function(e){
        e.preventDefault();
        $('#loginError').text('');
        var username = $.trim($('#username').val());
        var password = $.trim($('#password').val());
        if(username == ''){
            $('#usernameError').show();
            return;
        }else{
            $('#usernameError').hide();
        }
        if(password == ''){
            $('#passwordError').show();
            return;
        }else{
            $('#passwordError').hide();
        }
        $.ajax({
            url: 'loginPost',
            type: 'post',
            data: {
                username: username,
                password: password
            },
            success: function(result){
                if(result.returnCode == 0){
                    window.location.href = 'index';
                }else{
                    $('#loginError').text(result.message).fadeIn();
                }
            }
        })
    });
});