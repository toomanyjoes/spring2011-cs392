var userSelections = new Array();
var radioTracking = new Array();
var modelVariables;

function updateUI() {
    $('input').attr('disabled', false);  // enable all inputs
    for(feature in modelVariables) {
        if ( $('input[id='+feature+']').length ) {
            $('input[id='+feature+']').attr('checked', modelVariables[feature]['set']);

            // check if the user can change the variable
            $('input[id='+feature+']').attr('disabled', !modelVariables[feature]['chg']);
        }
    }
}

// used to send features selected by user
function buildSelectedString() {
    var str = '';
    for(feature in userSelections) {
        if(userSelections[feature])
            str += $('#'+feature).attr('value') + '=1&';
    }
    return str;
}

// used to send all set features. Specific to GPL
function buildSetFeatureString() {
    var str = 'Base=1&';
    // implementation
    var implementation = '';
    if('AL' in modelVariables && modelVariables['AL']['set'])
        implementation = 'AL';
    else if('NL' in modelVariables && modelVariables['NL']['set'])
        implementation = 'NL';
    else if('EL' in modelVariables && modelVariables['EL']['set'])
        implementation = 'EL';
    else {
        alert("Choose an implementation value");
        return 'error';
    }
    str += implementation + '=2&'
    
    // Gtp
    var gtp = '';
    if('Directed' in modelVariables && modelVariables['Directed']['set'])
        gtp = 'Directed';
    else if('Undirected' in modelVariables && modelVariables['Undirected']['set'])
        gtp = 'Undirected'
    else {
        alert("Choose a gtp value");
        return 'error';
    }
    str += gtp + implementation + '=3&';
    
    // Wgt
    var featureCount = 4
    if('Weighted' in modelVariables && modelVariables['Weighted']['set']) {
        str += 'Weighted' + implementation + '=4&';
        featureCount++;
    }
    else if('Unweighted' in modelVariables && modelVariables['Unweighted']['set'])
        ;
    else {
        alert("Choose a wgt value");
        return 'error';
    }
    
    // Src
    if('DFS' in modelVariables && modelVariables['DFS']['set']) {
        str += 'DFS=' + featureCount + '&';
        featureCount++;
    }
    else if('BFS' in modelVariables && modelVariables['BFS']['set']) {
        str += 'BFS=' + featureCount + '&';
        featureCount++;
    }
    
    // Alg
    if('Number' in modelVariables && modelVariables['Number']['set']) {
        str += 'Number=' + featureCount + '&';
        featureCount++;
        if( !('DFS' in modelVariables && modelVariables['DFS']['set']) && !('BFS' in modelVariables && modelVariables['BFS']['set']) ) {
            alert('Select a value in Src');
            return 'error';
        }
    }
    if('Connected' in modelVariables && modelVariables['Connected']['set']) {
        str += 'Connected=' + featureCount + '&';
        featureCount++;
        if( !('DFS' in modelVariables && modelVariables['DFS']['set']) && !('BFS' in modelVariables && modelVariables['BFS']['set']) ) {
            alert('Select a value in Src');
            return 'error';
        }
    }
    if('StronglyConnected' in modelVariables && modelVariables['StronglyConnected']['set']) {
        str += 'Transpose=' + featureCount + '&';
        featureCount++;
        str += 'StronglyConnected=' + featureCount + '&';
        featureCount++;
    }
    if('Cycle' in modelVariables && modelVariables['Cycle']['set']) {
        str += 'Cycle=' + featureCount + '&';
        featureCount++;
    }
    if('MSTPrim' in modelVariables && modelVariables['MSTPrim']['set']) {
        if(implementation == 'AL' || implementation == 'NL') {
            str += 'MSTPrim' + implementation + '=' + featureCount + '&';
            featureCount++;
        }
        str += 'MSTPrim=' + featureCount + '&';
        featureCount++;
    }
    if('MSTKruskal' in modelVariables && modelVariables['MSTKruskal']['set']) {
        if(implementation == 'AL' || implementation == 'NL') {
            str += 'MSTKruskal' + implementation + '=' + featureCount + '&';
            featureCount++;
        }
        str += 'MSTKruskal=' + featureCount + '&';
        featureCount++;
    }
    
    str += 'Benchmark=' + featureCount + '&';
    featureCount++;
    str += 'Prog='+featureCount
    return str;
}

function composeFeatures() {
    var setString = buildSetFeatureString();
    if(setString != 'error')
        window.location.href = "compose.cgi?"+setString;
}

function clearSelections() {
    modelVariables = null;
    $('input').attr('disabled', false);  // enable all inputs
    $('input:radio').each(function() { 
        userSelections[this.id] = false;
        radioTracking[this.name] = false;
        $(this).attr('checked', false);
    });
    $('input:checkbox').each(function() {
        userSelections[this.id] = false;
        $(this).attr('checked', false);
    });
}

function sendSelections() {
    var selectedString = buildSelectedString();
    $.blockUI();
    $.ajax({ 
        type: 'GET',
        url: 'gpl.cgi', 
        cache: false,
        dataType: 'json',
        data: selectedString,
        success: function(variableMap) {
            modelVariables = variableMap;
            updateUI();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("Error: " + textStatus);
        },
        complete: function() { 
            $.unblockUI();   // unblock when remote call returns 
        }
    });
}

$(document).ready(function() { 

    // init userSelections
    $('input:radio').each(function() { 
        userSelections[this.id] = false;
        radioTracking[this.name] = false;
    });
    $('input:checkbox').each(function() {
        userSelections[this.id] = false;
    });

    // ask the server to re-calculate available features after selection
    $('input:radio').click(function() {
        radioTracking[this.name] = !radioTracking[this.name];
        $(this).attr('checked', radioTracking[this.name]);
        userSelections[this.id] = !userSelections[this.id];
        sendSelections();
    });
    $('input:checkbox').click(function() { 
        userSelections[this.id] = !userSelections[this.id];
        sendSelections();
    });
    $('#submit').click(function() { 
        composeFeatures();
    });
    $('#reset').click(function() { 
        clearSelections();
    });

    // put each input in a <div>. Needed to display hover reasons on disabled inputs
    $('input').each(function() {
        $(this).wrap('<div id="'+this.id+'div" name="'+this.id+'" class="featureDiv" />');
    });
    // setup explanation text when hover over the inputs
    $('.featureDiv').hover(
        function () {
            $('#reason').html(modelVariables[$(this).attr('name')]['exp']);
        }, 
        function () {
            $('#reason').text("");
        }
    );
});
