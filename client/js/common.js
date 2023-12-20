/*******************************************************************************
 * COMMON
 *******************************************************************************/

////////////////////////////////////////////////////////////////////////////////
// STRING
////////////////////////////////////////////////////////////////////////////////

// Trim

if (!String.prototype.trim) {
  String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/gm, "");
  };
}

if (!String.prototype.ltrim) {
  String.prototype.ltrim = function () {
    return this.replace(/^\s+/, "");
  };
}

if (!String.prototype.rtrim) {
  String.prototype.rtrim = function () {
    return this.replace(/\s+$/, "");
  };
}

// Compare
if (!String.prototype.startWith) {
  String.prototype.startWith = function (str) {
    return this.substr(0, str.length) == str;
  };
}

if (!String.prototype.endWith) {
  String.prototype.endWith = function (str) {
    return this.substr(this.length - str.length, str.length) == str;
  };
}

// Length
if (!String.prototype.bytelength) {
  String.prototype.bytelength = function () {
    var len = 0;
    var c = "";

    for (var i = 0; i < this.length; i++) {
      c = this.charCodeAt(i);
      len++;
      if ((0xac00 <= c && c <= 0xd7a3) || (0x3131 <= c && c <= 0x318e)) {
        len++;
      }
    }
    return len;
  };
}

// replace

if (!String.prototype.replaceAll) {
  String.prototype.replaceAll = function (searchStr, replaceStr) {
    return this.split(searchStr).join(replaceStr);
  };
}

if (!String.prototype.toCamelCase) {
  String.prototype.toCamelCase = function () {
    return this.toLowerCase().replace(/(\_[a-z])/g, function (arg) {
      return arg.toUpperCase().replace("_", "");
    });
  };
}

if (!String.prototype.toUnderCase) {
  String.prototype.toUnderCase = function () {
    return this.replace(/([A-Z])/g, function (arg) {
      return "_" + arg.toLowerCase();
    }).toUpperCase();
  };
}

if (!String.prototype.toFirstUpperCase) {
  String.prototype.toFirstUpperCase = function () {
    return this.substr(0, 1).toUpperCase() + this.substr(1, this.length - 1);
  };
}

if (!String.prototype.toCase) {
  String.prototype.toCase = function (type) {
    if (typeof type == "undefined" || type == null || type == "") {
      return this;
    } else if (type == "camel") {
      return this.toCamelCase();
    } else if (type == "Camel") {
      return this.toCamelCase().toFirstUpperCase();
    } else if (type == "under") {
      return this.toUnderCase().toLowerCase();
    } else if (type == "UNDER") {
      return this.toUnderCase();
    } else if (type == "upper") {
      return this.toUpperCase();
    } else if (type == "lower") {
      return this.toLowerCase();
    }
    return this;
  };
}

// Padding

if (!String.prototype.lpad) {
  String.prototype.lpad = function (len, chr) {
    var r = this;
    while (r.length < len) {
      r = chr + r;
    }
    return r;
  };
}

if (!String.prototype.rpad) {
  String.prototype.rpad = function (len, chr) {
    var r = this;
    while (r.length < len) {
      r += chr;
    }
    return r;
  };
}

if (!String.prototype.bytelpad) {
  String.prototype.bytelpad = function (len, chr) {
    var r = this;
    var bytelen = 0;
    var c = "";

    bytelen = 0;
    for (var i = 0; i < r.length; i++) {
      c = r.charCodeAt(i);
      bytelen++;
      if ((0xac00 <= c && c <= 0xd7a3) || (0x3131 <= c && c <= 0x318e)) {
        bytelen++;
      }
    }

    while (bytelen < len) {
      r = chr + r;
      bytelen++;
    }
    return r;
  };
}

if (!String.prototype.byterpad) {
  String.prototype.byterpad = function (len, chr) {
    var r = this;
    var bytelen = 0;
    var c = "";

    bytelen = 0;
    for (var i = 0; i < r.length; i++) {
      c = r.charCodeAt(i);
      bytelen++;
      if ((0xac00 <= c && c <= 0xd7a3) || (0x3131 <= c && c <= 0x318e)) {
        bytelen++;
      }
    }

    while (bytelen < len) {
      r += chr;
      bytelen++;
    }
    return r;
  };
}
// etc


if (!String.prototype.removeDblSpace) {
  String.prototype.removeDblSpace = function () {
    var str = this.replace(/ /gm, " ");
    while (str.indexOf("  ") > -1) {
      str = str.replace(/  /gm, " ");
    }
    return str;
  };
}

if (!String.prototype.loopCut) {
  String.prototype.loopCut = function (lstr, rstr) {
    var s = this;
    var loopcnt = 0;
    var lidx = s.indexOf(lstr);
    var ridx = s.indexOf(rstr);

    while (lidx > -1 || ridx > -1) {
      if (lidx < 0 || lidx > ridx) {
        s = s.substr(ridx + rstr.length, s.length - ridx - rstr.length);
        loopcnt--;
      } else {
        s = s.substr(lidx + lstr.length, s.length - lidx - lstr.length);
        loopcnt++;
      }
      if (loopcnt <= 0) {
        break;
      }
      lidx = s.indexOf(lstr);
      ridx = s.indexOf(rstr);
    }
    if (loopcnt > 0) {
      s = "";
    }

    return s;
  };
}

if (!String.prototype.loopCutRange) {
  String.prototype.loopCutRange = function (sstr, estr, cstr) {
    var s = this;
    var r = "";
    var loopcnt = 0;
    var lidx = 0;
    var eidx = 0;
    var k = 0;

    sidx = s.indexOf(sstr);
    eidx = s.indexOf(estr);

    while (sidx > -1 && eidx > -1) {
      r += s.substr(0, sidx);
      r += cstr;
      loopcnt = 0;

      while (sidx > -1 || eidx > -1) {
        if (sidx < 0 || sidx > eidx) {
          s = s.substr(eidx + estr.length, s.length - eidx - estr.length);
          loopcnt--;
        } else {
          s = s.substr(sidx + sstr.length, s.length - sidx - sstr.length);
          loopcnt++;
        }
        if (loopcnt <= 0) {
          break;
        }
        sidx = s.indexOf(sstr);
        eidx = s.indexOf(estr);
      }

      if (loopcnt > 0) {
        s = "";
      }
      sidx = s.indexOf(sstr);
      eidx = s.indexOf(estr);
    }
    if (sidx > -1) {
      r += s.substr(0, sidx);
    } else {
      r += s;
    }
    return r;
  };
}

if (!String.prototype.replaceRange) {
  String.prototype.replaceRange = function (sstr, estr, cstr) {
    var s = this;
    var r = "";

    var sidx = s.indexOf(sstr);
    var eidx = 0;

    while (sidx > -1) {
      r += s.substr(0, sidx);
      r += cstr;
      s = s.substr(sidx + sstr.length, s.length - sidx - sstr.length);
      eidx = s.indexOf(estr);
      if (eidx <= -1) {
        s = "";
        break;
      }
      s = s.substr(eidx + estr.length, s.length - eidx - estr.length);
      sidx = s.indexOf(sstr);
    }
    r += s;
    return r;
  };
}

////////////////////////////////////////////////////////////////////////////////
// COMMON MODULE
////////////////////////////////////////////////////////////////////////////////
var Sequence = function () {
  this.no = -1;
};

Sequence.prototype.nextval = function () {
  return ++this.no;
};

Sequence.prototype.currval = function () {
  return this.no;
};

////////////////////////////////////////////////////////////////////////////////
// COMMON MODULE
////////////////////////////////////////////////////////////////////////////////
var randomNumber = function (min, max) {
  var ranNum = Math.floor(Math.random() * (max - min + 1)) + min;
  return ranNum;
};

var randomString = function (length) {
  var result = "";
  var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  var charactersLength = characters.length;
  for (var i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
};
