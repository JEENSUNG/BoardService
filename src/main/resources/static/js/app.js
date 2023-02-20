const main = {
    init : function() {
        const _this = this;
        // 게시글 저장
        $('#btn-save').on('click', function () {
            _this.save();
        });
        // 게시글 수정
        $('#btn-update').on('click', function () {
            _this.update();
        });
        // 게시글 삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        //비밀 게시판 저장
        $('#btn-secret-save').on('click', function () {
            _this.secretSave();
        });
        // 비밀 게시판 수정
        $('#btn-secret-update').on('click', function () {
            _this.secretUpdate();
        });
        // 비밀 게시판 삭제
        $('#btn-secret-delete').on('click', function () {
            _this.secretDelete();
        });

        // 회원 수정
        $('#btn-user-modify').on('click', function () {
            _this.userModify();
        });

        // 댓글 저장
        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });

         $('#btn-secret-comment-save').on('click', function () {
            _this.secretCommentSave();
        });

        //쪽지 보내기
        $('#btn-letter-save').on('click', function () {
            _this.letterSave();
        });

        $('#btn-post-letter-save').on('click', function () {
            _this.postLetterSave();
        });

        //쪽지 삭제
        $('#btn-delete-letter').on('click', function() {
            _this.deleteLetter();
        });

        $('#btn-delete-letter2').on('click', function () {
            _this.deleteLetter2();
        });

        //신고하기
        $('#btn-post-report-save').on('click', function () {
            _this.postReportSave();
        });
        //회원 강등
        $('#btn-delete-role').on('click', function () {
            _this.userDeleteRole();
        });
        //포인트 회수
        $('#btn-delete-point').on('click', function () {
            _this.userDeletePoint();
        })
        //신고내역 삭제
        $('#btn-delete-report').on('click', function () {
            _this.userDeleteReport();
        })
        $('#btn-like').on('click', function () {
            _this.like();
        });
        $('#btn-disLike').on('click', function () {
            _this.disLike();
        });
        $('#btn-secret-like').on('click', function () {
            _this.secretLike();
        });
        $('#btn-secret-disLike').on('click', function () {
            _this.secretDisLike();
        });
        // 댓글 수정
        document.querySelectorAll('#btn-comment-update').forEach(function (item) {
            item.addEventListener('click', function () { // 버튼 클릭 이벤트 발생시
                const form = this.closest('form'); // btn의 가장 가까운 조상의 Element(form)를 반환 (closest)
                _this.commentUpdate(form); // 해당 폼으로 업데이트 수행
            });
        });
        document.querySelectorAll('#btn-secret-comment-update').forEach(function (item) {
            item.addEventListener('click', function () { // 버튼 클릭 이벤트 발생시
                const form = this.closest('form'); // btn의 가장 가까운 조상의 Element(form)를 반환 (closest)
                _this.secretCommentUpdate(form); // 해당 폼으로 업데이트 수행
            });
        });
    },

    /** 글 작성 */
    save : function () {
        const data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        // 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/posts',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (dat, status, xhr) {
                alert('등록되었습니다.');
                window.location.href = '/posts/read/' + JSON.parse(dat.data);
                if(JSON.parse(dat.vip)){
                    alert("축하합니다! VIP 등급으로 승급하셨니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 비밀글 작성 */
    secretSave : function () {
        const data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        // 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/secrets/posts',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (dat, status, xhr) {
                alert('등록되었습니다.');
                window.location.href = '/secrets/posts/read/' + JSON.parse(dat.data);
                if(JSON.parse(dat.vip)){
                    alert("축하합니다! VIP 등급으로 승급하셨니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 글 수정 */
    update : function () {
        const data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            } else {
                $.ajax({
                    type: 'PUT',
                    url: '/api/posts/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert("수정되었습니다.");
                    window.location.href = '/posts/read/' + data.id;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    secretUpdate : function () {
        const data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            } else {
                $.ajax({
                    type: 'PUT',
                    url: '/api/secrets/posts/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert("수정되었습니다.");
                    window.location.href = '/secrets/posts/read/' + data.id;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    /** 글 삭제 */
    delete : function () {
        const id = $('#id').val();
        const con_check = confirm("정말 삭제하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'DELETE',
                url: '/api/posts/'+id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'

            }).done(function () {
                alert("삭제되었습니다.");
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    secretDelete : function () {
        const id = $('#id').val();
        const con_check = confirm("정말 삭제하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'DELETE',
                url: '/api/secrets/posts/'+id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'

            }).done(function () {
                alert("삭제되었습니다.");
                window.location.href = '/secrets/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    /** 회원 수정 */
    userModify : function () {
        const data = {
            id: $('#id').val(),
            modifiedDate: $('#modifiedDate').val(),
            username: $('#username').val(),
            nickname: $('#nickname').val(),
            password: $('#password').val()
        }
        if(!data.nickname || data.nickname.trim() === "" || !data.password || data.password.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else if(!/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/.test(data.password)) {
            alert("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
            $('#password').focus();
            return false;
        } else if(!/^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/.test(data.nickname)) {
            alert("닉네임은 특수문자를 제외한 2~10자리여야 합니다.");
            $('#nickname').focus();
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            $.ajax({
                type: "PUT",
                url: "/api/user",
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)

            }).done(function () {
                alert("회원수정이 완료되었습니다.");
                window.location.href = "/";

            }).fail(function (error) {
                if (error.status === 500) {
                    alert("이미 사용중인 닉네임 입니다.");
                    $('#nickname').focus();
                } else {
                    alert(JSON.stringify(error));
                }
            });
        } else {
            return false;
        }
    },

    /** 댓글 저장 */
    commentSave : function () {
        const data = {
            postsId: $('#postsId').val(),
            comment: $('#comment').val()
        }

        // 공백 및 빈 문자열 체크
        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/posts/' + data.postsId + '/comments',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (dat, status, xhr) {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
                if(JSON.parse(dat.vip)){
                    alert("축하합니다! VIP 등급으로 승급하셨니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    secretCommentSave : function () {
        const data = {
            postsId: $('#postsId').val(),
            comment: $('#comment').val()
        }

        // 공백 및 빈 문자열 체크
        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/secrets/posts/' + data.postsId + '/comments',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (dat, status, xhr) {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
                if(JSON.parse(dat.vip)){
                    alert("축하합니다! VIP 등급으로 승급하셨니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },
    /** 댓글 수정 */
    commentUpdate : function (form) {
        const data = {
            id: form.querySelector('#id').value,
            postsId: form.querySelector('#postsId').value,
            comment: form.querySelector('#comment-content').value,
            writerUserId: form.querySelector('#writerUserId').value,
            sessionUserId: form.querySelector('#sessionUserId').value
        }
        console.log("commentWriterID : " + data.writerUserId);
        console.log("sessionUserID : " + data.sessionUserId);

        if (data.writerUserId !== data.sessionUserId) {
            alert("본인이 작성한 댓글만 수정 가능합니다.");
            return false;
        }

        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            $.ajax({
                type: 'PUT',
                url: '/api/posts/' + data.postsId + '/comments/' + data.id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    secretCommentUpdate : function (form) {
        const data = {
            id: form.querySelector('#id').value,
            postsId: form.querySelector('#postsId').value,
            comment: form.querySelector('#comment-content').value,
            writerUserId: form.querySelector('#writerUserId').value,
            sessionUserId: form.querySelector('#sessionUserId').value
        }
        console.log("commentWriterID : " + data.writerUserId);
        console.log("sessionUserID : " + data.sessionUserId);

        if (data.writerUserId !== data.sessionUserId) {
            alert("본인이 작성한 댓글만 수정 가능합니다.");
            return false;
        }

        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            $.ajax({
                type: 'PUT',
                url: '/api/secrets/posts/' + data.postsId + '/comments/' + data.id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 댓글 삭제 */
    commentDelete : function (postsId, commentId, commentWriterId, sessionUserId) {

        // 본인이 작성한 글인지 확인
        if (commentWriterId !== sessionUserId) {
            alert("본인이 작성한 댓글만 삭제 가능합니다.");
        } else {
            const con_check = confirm("삭제하시겠습니까?");

            if (con_check === true) {
                $.ajax({
                    type: 'DELETE',
                    url: '/api/posts/' + postsId + '/comments/' + commentId,
                    dataType: 'JSON',
                }).done(function () {
                    alert('댓글이 삭제되었습니다.');
                    window.location.reload();
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    secretCommentDelete : function (postsId, commentId, commentWriterId, sessionUserId) {

        // 본인이 작성한 글인지 확인
        if (commentWriterId !== sessionUserId) {
            alert("본인이 작성한 댓글만 삭제 가능합니다.");
        } else {
            const con_check = confirm("삭제하시겠습니까?");

            if (con_check === true) {
                $.ajax({
                    type: 'DELETE',
                    url: '/api/secrets/posts/' + postsId + '/comments/' + commentId,
                    dataType: 'JSON',
                }).done(function () {
                    alert('댓글이 삭제되었습니다.');
                    window.location.reload();
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    like : function (form) {
        const id = $('#id').val();
        const con_check = confirm("추천하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'POST',
                url: '/api/posts/'+ id + '/like',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert("추천하였습니다.");
                window.location.href = '/posts/read/' + id;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    secretLike : function (form) {
        const id = $('#id').val();
        const con_check = confirm("추천하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'POST',
                url: '/api/secrets/posts/'+ id + '/like',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert("추천하였습니다.");
                window.location.href = '/secrets/posts/read/' + id;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    disLike : function (form) {
        const id = $('#id').val();
        const con_check = confirm("비추천하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'POST',
                url: '/api/posts/'+ id + '/disLike',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert("비추천하였습니다.");
                window.location.href = '/posts/read/' + id;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    secretDisLike : function (form) {
        const id = $('#id').val();
        const con_check = confirm("비추천하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'POST',
                url: '/api/secrets/posts/'+ id + '/disLike',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert("비추천하였습니다.");
                window.location.href = '/secrets/posts/read/' + id;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },

    letterSave : function () {
        const data = {
            id: $('#id').val(),
            userId: $('#userId').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };
        const con_check = confirm("쪽지를 보내시겠습니까?");
        if(data.userId == data.id){
            alert("자기 자신에게는 쪽지를 보낼 수 없습니다.");
            return false;
        }// 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            if(con_check == true){
                $.ajax({
                    type: 'POST',
                    url: '/api/user/letters/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert('성공적으로 보냈습니다.');
                    window.location.href = '/userList';
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    postLetterSave : function () {
        const data = {
            id: $('#id').val(),
            pageNum: $('#pageNum').val(),
            userId: $('#userId').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };
        const con_check = confirm("쪽지를 보내시겠습니까?");
        if(data.userId == data.id){
            alert("자기 자신에게는 쪽지를 보낼 수 없습니다.");
            return false;
        }
        // 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            if(con_check == true){
                $.ajax({
                    type: 'POST',
                    url: '/api/posts/' + data.pageNum + '/letters/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert('성공적으로 보냈습니다.');
                    window.location.href = '/posts/read/' + data.pageNum;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

     postReportSave: function () {
          const data = {
              id: $('#id').val(),
              pageNum: $('#pageNum').val(),
              userId: $('#userId').val(),
              title: $('#title').val(),
              content: $('#content').val()
          };
          const con_check = confirm("신고하시겠습니까?");
          if(data.userId == data.id){
              alert("자기 자신의 게시글은 신고할 수 없습니다.");
              return false;
          }
          // 공백 및 빈 문자열 체크
          if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
              alert("공백 또는 입력하지 않은 부분이 있습니다.");
              return false;
          } else {
              if(con_check == true){
                  $.ajax({
                      type: 'POST',
                      url: '/api/posts/' + data.pageNum + '/report',
                      dataType: 'JSON',
                      contentType: 'application/json; charset=utf-8',
                      data: JSON.stringify(data)
                  }).done(function () {
                      alert('성공적으로 보냈습니다.');
                      window.location.href = '/posts/read/' + data.pageNum;
                  }).fail(function (error) {
                      alert(JSON.stringify(error));
                  });
              }
          }
     },
     userDeleteRole : function () {
        const data = {
             id: $('#id').val()
         }
         $.ajax({
             type: 'PUT',
             url: '/api/admin/report/' + data.id,
             dataType: 'JSON',
             contentType: 'application/json; charset=utf-8',
             data: JSON.stringify(data)
         }).done(function () {
             alert('정상 처리되었습니다.');
             window.location.reload();
         }).fail(function (error) {
             alert(JSON.stringify(error));
         });
     },
     userDeleteReport : function (){
         const data = {
              id: $('#id').val()
          }
          $.ajax({
              type: 'DELETE',
              url: '/api/admin/report/' + data.id,
              dataType: 'JSON',
              contentType: 'application/json; charset=utf-8',
              data: JSON.stringify(data)
          }).done(function () {
              alert('정상 처리되었습니다.');
              window.location.reload();
          }).fail(function (error) {
              alert(JSON.stringify(error));
          });
     },

     userDeletePoint : function () {
        const data = {
            id: $('#id').val()
            }
           $.ajax({
               type: 'DELETE',
               url: '/api/admin/report/deletePoint/' + data.id,
               dataType: 'JSON',
               contentType: 'application/json; charset=utf-8',
               data: JSON.stringify(data)
           }).done(function () {
               alert('정상 처리되었습니다.');
               window.location.reload();
           }).fail(function (error) {
               alert(JSON.stringify(error));
           });
     },

     deleteLetter : function () {
        const data = {
            id: $('#id').val()
            }
           $.ajax({
               type: 'DELETE',
               url: '/api/sendLetters/delete/' + data.id,
               dataType: 'JSON',
               contentType: 'application/json; charset=utf-8',
               data: JSON.stringify(data)
           }).done(function () {
               alert('정상 처리되었습니다.');
               window.location.reload();
           }).fail(function (error) {
               alert(JSON.stringify(error));
           });
     },

     deleteLetter2 : function(){
        const data = {
             id: $('#id').val()
             }
            $.ajax({
                type: 'DELETE',
                url: '/api/takenLetters/delete/' + data.id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('정상 처리되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
     }
};

main.init();