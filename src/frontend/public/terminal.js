function smart_split(input, del, empty_space) {
    if (input.length === 0) return input;
    var outputs = [""];
    
    var compare = function(base, insert, position) {
        if ((position + insert.length) > base.length) return false;
        for (var i = 0; i < insert.length; i++) {
            if (!(base.charAt(position + i) === insert.charAt(i))) return false;
        }
        return true;
    };
    
    var quotes = false;
    for (var i = 0; i < input.length; i++) {
        var char = input.charAt(i);
        if (char === '"') {
            quotes = !quotes;
            continue;
        }
        
        if (!quotes && compare(input, del, i)) {
            outputs.push("");
            i += del.length - 1;
            continue;
        }
        
        outputs[outputs.length - 1] += char;
    }
    
    if (!empty_space) {
        for (var i = 0; i < outputs.length; i++) {
            if (outputs[i] === "") {
                outputs.splice(i, 1);
            }
        }
    }
    
    return outputs;
}

var terminal_user_title = "C: Undefined/username";

function update_user_title(title) {
    terminal_user_title = title;
    document.getElementById("input_title").innerText = terminal_user_title + " > ";
}

update_user_title(terminal_user_title);

var current_block;

function new_block() {
    var wrapper = document.getElementById('wrapper');
    current_block = document.createElement("div");
    current_block.classList.add("log");
    wrapper.appendChild(current_block);
}

function block_log(message) {
    current_block.innerHTML += "<p>" + message + "</p>";
}

function log(message) {
    var wrapper = document.getElementById('wrapper');
    wrapper.innerHTML += "<div class='log'><p>" + message + "</p></div>";
}

document.getElementById('input_source').onblur = function() {
    document.getElementById("input_source").focus();
};

document.getElementById('input_source').addEventListener('keyup', submit_command);

var registry = new Map();

function register_cmd(cmd_name, func) {
    registry.set(cmd_name.toString().toUpperCase(), func);
}

function submit_command() {
    event.preventDefault();
    if (!(event.keyCode === 13)) return;
    var command = document.getElementById("input_source").value;
    document.getElementById("input_source").value = "";
    
    new_block();
    block_log(terminal_user_title + " > " + command);
    
    if (registry.has(command.split(" ")[0].toUpperCase())) {
        registry.get(command.split(" ")[0].toUpperCase())(command);
    } else {
        fetch('/post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                sdf4g6sdff4g5fg4: "dsggf4h6dghfg6h4g5h4g54hgf9fdg8fff",
                ssd4f6ds4g5df4gd: "d4465gfds456g4f56fd4sg5f64sgfd84f6",
                a6df4g64fdf4f5ff: "d4df65g4fd6sh4g6f84hgf654hg5fh46f8",
                sd4fdf4g4g54ffff: btoa(command),
                s4f4dgf564g54f55: "d4fdgdfsgh5fds4h56gf48g47f5gf47hgr"
            })
        }).then((res) => {
            if(res.status == 200){
                res.text().then((responseText) => {
                    var output = atob(JSON.parse(responseText).res);
                    block_log(output);
                });
            }
        });
        //block_log("'" + command.split(" ")[0].toUpperCase() + "' is not a registered command, please refer to documentation for proper usage.");
    }
}

function reset(){
    wrapper.innerHTML = "<div class='log'><p><b>Remote Server</b></p></div><div class='log'>Welcome to online simulated shell!</p></div>";
}

register_cmd("clear", (cmd) => {
    reset();
})

register_cmd("cls", (cmd) => {
    reset();
})

register_cmd("help", function(cmd) {
    block_log("Command List: ");
    registry.forEach(function(value, key, map) {
        block_log("    - " + key);
    });
});
