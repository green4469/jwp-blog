let commentList = document.getElementById("comment-list");
commentList.addEventListener("click", function (ev) {
    if (event.target.classList.contains("target")) {
        event.target.parentNode.parentNode.classList.toggle('editing');
    }
    console.log(event.target.classList);
    console.log(event.target.dataset.whichButton);
    if (event.target.classList.contains("btn") && event.target.dataset.whichButton === 'delete') {
        console.log("remove btn works")
        console.log(event.target.dataset.commentId)
        fetch('/comments/' + event.target.dataset.commentId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        }).then(function(response) {
            return response.json();
        }).then(function(json) {
            let comments = document.getElementById("comment-list");

            // Remove all children nodes
            var child = comments.lastElementChild;
            while (child) {
                comments.removeChild(child);
                child = comments.lastElementChild;
            }

            // Add All comments
            for (var i = 0; i < json.length; i++){
                var obj = json[i];
                let compiledNode = commentCompiledTemplate({"commenter": obj.commenter, "comment": obj.comment, "id": obj.id, "minAgo": obj.minAgo});
                console.log(compiledNode);
                console.log(typeof compiledNode);

                comments.insertAdjacentHTML('beforeend', commentCompiledTemplate({"commenter": obj.commenter, "comment": obj.comment, "id": obj.id, "minAgo": obj.minAgo}));
            }
        })
    }
    if (event.target.classList.contains("btn") && event.target.dataset.whichButton === 'update') {
        console.log("update btn works")
        let requestDto = {
            comment: event.target.parentNode.children[1].innerHTML
        }

        fetch('/comments/' + event.target.dataset.commentId, {
            method: 'UPDATE',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(requestDto)
        }).then(function(response) {
            return response.json();
        }).then(function(json) {
            let comments = document.getElementById("comment-list");

            // Remove all children nodes
            var child = comments.lastElementChild;
            while (child) {
                comments.removeChild(child);
                child = comments.lastElementChild;
            }

            // Add All comments
            for (var i = 0; i < json.length; i++){
                var obj = json[i];
                let compiledNode = commentCompiledTemplate({"commenter": obj.commenter, "comment": obj.comment, "id": obj.id, "minAgo": obj.minAgo});
                console.log(compiledNode);
                console.log(typeof compiledNode);

                comments.insertAdjacentHTML('beforeend', commentCompiledTemplate({"commenter": obj.commenter, "comment": obj.comment, "id": obj.id, "minAgo": obj.minAgo}));
            }
        })
    }
});